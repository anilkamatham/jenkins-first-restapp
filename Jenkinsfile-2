pipeline {
    agent {
        label 'jenkins-slave2'
    }
    parameters {
        string(name: 'tomcat_dev', defaultValue: '52.66.69.64', description: 'staging server')        
        string(name: 'tomcat_prod', defaultValue: '13.127.36.54', description: 'production server')               
    }
    
    tools {
        maven 'localmaven'
    }
    triggers {
        pollSCM('* * * * *')
    }
    stages {
       stage('Build') {         
           steps {
                echo 'compiling and testing the code...'
                sh 'mvn clean package'                 
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
       stage('Deploy to prod') {                                  
                steps {                
                    //sshagent(['ec2-tomcat-stage']) {
                      //  sh "scp -o StrictHostKeyChecking=no **/*.war tomcat-stage@${params.tomcat_dev}:/usr/share/tomcat/webapps"
                    //  sh "scp -i /home/jenkins-slave1/.ssh/id_rsa **/*.war tomcat-stage@${params.tomcat_dev}:/usr/share/tomcat/webapps"
                    sshagent(['ec2-tomcat-prod']){
                        sh "scp -i ~/.ssh/id_rsa_slave2_prod **/*.war tomcat-prod@${params.tomcat_prod}:/usr/share/tomcat/webapps"
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
       }
}