pipeline {
    agent none
    parameters {
        string(name: 'tomcat_dev', defaultValue: '52.66.69.64', description: 'staging server')        
        string(name: 'tomcat_prod', defaultValue: ' 13.127.36.54', description: 'production server')               
    }
    
    tools {
        maven 'localmaven'
    }
    triggers {
        pollSCM('* * * * *')
    }
    stages {
       stage('Build') {
         agent any  
           steps {
                echo 'compiling and testing the code...'
                sh 'mvn clean package'  
                stash includes: '**/target/*.war', name: 'appartifact'
           }
           post {
                success {
                    echo 'Archieving the artifcats'
                    archiveArtifacts artifacts: '**/target/*.war'
                }
                failure {
                    echo 'failed to build the code'
                }
           }
       }
       stage('Deployment') {
          parallel {  
            stage('Deploy to stage') {
                agent {
                    label 'jenkins-slave1'
                }
                steps {
                    unstash 'appartifact'
                    sshagent(['ec2-tomcat-stage']) {
                        sh "scp -o StrictHostKeyChecking=no **/*.war tomcat-stage@${params.tomcat_dev}:/usr/share/tomcat/webapps"
                    }
                }
                    post{
                            success {
                                echo 'Successfully deployed to stage'                   
                            }
                            failure {
                                echo 'Failed to deployed to stage'
                            }
                    } 
            } 
            stage('Deploy to prod') {
                agent {
                    label 'jenkins-slave2'
                }
                steps {                 
                        unstash 'appartifact'
                        sh "scp -i ~/.ssh/id_rsa_slave2_prod **/*.war tomcat-stage@${params.tomcat_prod}:/usr/share/tomcat/webapps"
                }
                    post{
                            success {
                                echo 'Successfully deployed to stage'                   
                            }
                            failure {
                                echo 'Failed to deployed to stage'
                            }
                    } 
            }  
         }            
       }
    }
}