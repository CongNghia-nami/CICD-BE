pipeline {
    agent { label 'gitlab-bride' }

    environment {
        //DOCKER_IMAGE = "${env.REGISTRY_URL}/${env.REGISTRY_PROJECT}/${env.CI_PROJECT_NAME}:${env.CI_COMMIT_TAG}_${env.CI_COMMIT_SHORT_SHA}"
        DOCKER_IMAGE = "${env.REGISTRY_URL}/${env.REGISTRY_PROJECT}/${env.CI_PROJECT_NAME ?: 'default_project'}:${env.CI_COMMIT_TAG ?: 'latest'}_${env.CI_COMMIT_SHORT_SHA ?: '0000000'}"
        DOCKER_CONTAINER = "${env.CI_PROJECT_NAME}"
    }

    stages {
        stage('Build and Push') {
            environment {
                GIT_STRATEGY = 'clone'
            }
            steps {
                script {
                    sh "docker login ${env.REGISTRY_URL} -u ${env.REGISTRY_USER} -p ${env.REGISTRY_PASSWORD}"
                    sh "docker build -t ${DOCKER_IMAGE} ."
                    sh "docker push ${DOCKER_IMAGE}"
                }
            }
        }

        stage('Deploy') {
            environment {
                GIT_STRATEGY = 'none'
            }
            steps {
                script {
                    sh "docker login ${env.REGISTRY_URL} -u ${env.REGISTRY_USER} -p ${env.REGISTRY_PASSWORD}"
                    sh "docker pull ${DOCKER_IMAGE}"
                    sh "docker stop ${DOCKER_CONTAINER} || true"
                    sh "docker rm ${DOCKER_CONTAINER} || true"
                    sh "docker run --name ${DOCKER_CONTAINER} -dp 2727:8085 ${DOCKER_IMAGE}"
                }
            }
        }

        stage('Check Logs') {
            steps {
                script {
                    sh "docker logs -f ${DOCKER_CONTAINER}"
                }
            }
        }
    }
}
