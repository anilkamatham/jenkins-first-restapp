pipeline {
    agent {label 'slave-1'}
    parameters {
        string(name: 'tomcat_dev', defaultValue: '65.0.4.169', description: 'Staging server' ) 
    }
    
    triggers {
        pollSCM('* * * * *')
    }
    stages{
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                 echo 'Archiving Now...'
                 archiveArtifacts artifacts: '**/target/*.war'
                } 
            } 
            
            
        }
        stage('Deployment') {

                     steps {
                        sshagent(['ec2-tomcat']){
                            sh "scp -o StrictHostKeyChecking=no **/*.war ec2-user@${params.tomcat_dev}:/usr/share/tomcat/webapps"
                        } 
                      // sh "scp -i ~/.ssh/id_rsa **/*.war ec2-user@${params.tomcat_dev}:/usr/share/tomcat/webapps" 

                     }                
        }
    }
 }
