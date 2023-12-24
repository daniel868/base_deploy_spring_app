provider "kubernetes" {
  host                   = data.aws_eks_cluster.cluster.endpoint
  token                  = data.aws_eks_cluster_auth.cluster.token
  cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate_authority.0.data)
}

provider "aws" {
  region = var.region
}

data "aws_availability_zones" "available" {}

locals {
  cluster_name = "cc-eks-${random_string.suffix.result}"
}

resource "random_string" "suffix" {
  length  = 8
  special = false
}

resource "kubernetes_config_map" "api_config" {
  metadata {
    name = "api-config"
  }
  data = {
    SPRING_PROFILES_ACTIVE = "k8s"
  }
}

resource "kubernetes_config_map" "db-config" {
  metadata {
    name = "db-config"
  }
  data = {
    POSTGRES_USER     = "admin"
    POSTGRES_PASSWORD = "admin"
  }
}

resource "kubernetes_deployment" "api_pod" {
  metadata {
    name   = "api-pod"
    labels = {
      app = "api-pod"
    }
  }

  spec {
    replicas = 2

    selector {
      match_labels = {
        app = "api-pod"
      }
    }

    template {
      metadata {
        name   = "api-pod"
        labels = {
          app = "api-pod"
        }
      }

      spec {
        container {
          name  = "api-pod"
          image = "alexandrudaniel/my-spring-app:latest"

          port {
            container_port = 8080
          }

          env_from {
            config_map_ref {
              name = "api-config"
            }
          }
        }
      }
    }
  }
}

resource "kubernetes_deployment" "postgresql" {
  metadata {
    name   = "postgresql"
    labels = {
      app = "postgresql"
    }
  }
  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "postgresql"
      }
    }

    template {
      metadata {
        name   = "postgresql"
        labels = {
          app = "postgresql"
        }
      }

      spec {
        container {
          name              = "postgresql"
          image             = "postgres:10.4"
          image_pull_policy = "IfNotPresent"

          port {
            container_port = 5432
          }

          env_from {
            config_map_ref {
              name = "db-config"
            }
          }

        }
      }
    }
  }
}

resource "kubernetes_service" "api_service" {
  metadata {
    name = "api-service"
  }
  spec {
    selector = {
      app = "api-pod"
    }
    type = "LoadBalancer"
    port {
      port        = 80
      target_port = 8080
      node_port   = 31000
    }
  }
}

resource "kubernetes_service" "db-service" {
  metadata {
    name   = "db-service"
    labels = {
      app = "postgresql"
    }
  }
  spec {
    selector = {
      app = "postgresql"
    }

    type = "ClusterIP"
    port {
      port = 5432
    }
  }
}

#resource "kubernetes_persistent_volume_claim" "postgres-pv-volume" {
#  metadata {
#    name = "postgres-pv-volume"
#  }
#  spec {
#    storage_class_name = "manual"
#    access_modes       = ["ReadWriteOnce"]
#    resources {
#      requests = {
#        storage = "1Gi"
#      }
#    }
#  }
#}
