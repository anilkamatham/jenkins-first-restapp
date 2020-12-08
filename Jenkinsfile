pipeline {
    agent any
    parameters {
        string(name: 'tomcat-dev', defaultValue: '13.126.123.18', description: 'Staging server' )
        string(name: 'tomcat-prod', defaultValue: '15.206.148.111', description: 'Production server')        
    }
    triggers {
        pollSCM('* * * * *')
    }
    stages{
        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
            post {
                success {
                 echo 'Archiving Now...'
                 archiveArtifacts artifacts: '**/target/*.war'
                } 
            } 
        }
        stage('Deployment') {
            parallel {
                stage('Deploy to stage') {
                     steps {
                          bat "winscp -i C:\Technology\Javaworkspaces\JenkinProjects\jenkins-tomcat-key-pair1.pem **/target/*.war ec2-user@${params.tomcat-dev}:/var/lib/tomcat/webapps" 
                     }                
                }
                stage('Deploy to prod') {
                    steps {
                        bat "winscp -i C:\Technology\Javaworkspaces\JenkinProjects\jenkins-tomcat-key-pair1.pem **/target/*.war ec2-user@${params.tomcat-prod}:/var/lib/tomcat/webapps" 
                    }
                }

            }
        }
    }
}