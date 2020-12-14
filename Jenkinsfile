pipeline {
    agent {
        label 'jenkins-slave1'
    }
    parameters {
        string(name: 'dev_server', defaultValue: '52.66.69.64', description: 'dev server ip address')
        choice(name: 'branch', choices: ['master', 'dev'], description: 'branch to checkout and build')
        boolean(name: 'onlymaster', defaultValue: false, description: 'run only when dev branch is checkedout')
    }
    tools {
        maven 'localmaven'
    }
    triggers {
        pollSCM('* * * * *')
    }
    environment {      
        EXECUTE_STAGE_TEST = "no"
        ARTIFATCS_PATH= "**/*.war"
        USER_CREDENTIALS=credentials('anilcredentials')
        ARTIFACT_TO_COPY="**/*.war"
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'                
            } 
            post {
                success {
                    archiveArtifacts artifacts: ${env.ARTIFATCS_PATH}
                }
            }
            failure {
                echo "job ${JOB_NAME} with ${BUILD_ID} is failed"
            }               
        }
        stage('Test') {
           when {
               expression {
                   EXECUTE_STAGE_TEST=='yes'
               }
           }
           steps {
              echo 'Testing the application'
           }
        }
        stage('UAT') {
           when {
               expression {
                   params.onlymaster==true
               }
           }
           steps {
               echo 'UAT testing...'
           }
        }
        stage('Deploy to stage'){
             steps {
                 echo "user credentials using environmet varaible USER_CREDENTIALS ${env.USER_CREDENTIALS}"             
                 withCredentials(['usernamePassword(credentialsid: 'anilcredentials', usernameVariable: 'USER', passwordVariable: 'PWD']'){
                      echo "username: ${USER} password: ${PWD}"
                 }
               sshagent(['ec2-tomcat-stage']) {
                   sh "scp -o StrictHostKeyChecking=no ${env.ARTIFACT_TO_COPY} tomcat-stage@${params.tomcat_dev}:/usr/share/tomcat/webapps"
               }
             }
        }
    }
}