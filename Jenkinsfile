pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID = credentials('aws-access-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('aws-secret-access-key')
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/skycoder3899/Ecommerce.git'
            }
        }

        stage('Build Application') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ecommerce-app:1.0 .'
            }
        }

        stage('Push to AWS ECR') {
            steps {
                sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin your-aws-ecr-repo'
                sh 'docker tag ecommerce-app:1.0 your-aws-ecr-repo/ecommerce-app:1.0'
                sh 'docker push your-aws-ecr-repo/ecommerce-app:1.0'
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent(['ec2-key']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no ec2-user@your-ec2-ip << EOF
                        docker stop ecommerce_app || true
                        docker rm ecommerce_app || true
                        docker pull your-aws-ecr-repo/ecommerce-app:1.0
                        docker-compose up -d
                    EOF
                    '''
                }
            }
        }
    }
}
