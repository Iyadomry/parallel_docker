def main() {
    ansiColor('xterm') {
        docker1() 
    }
}

def docker1() {
    node(label: 'jenkins') {
        try {
            stage('remove all running containers') {
                sh 'docker rm -f $(docker ps -a -q)'
                }
            }
        catch (Exception) {
            echo ' There is no current containers to delete, try again '
            currentBuild.result = 'SUCCESS'
            }

        // stage('Deply my application as a container') {
        //     ansiColor('xterm') {
        //         echo "\u001B[31mbuild new container\u001B[0m"
        //         sh "docker run --name ${name1} -d --rm -p ${port1}:80  nginx"
        //         sh "docker run --name ${name2} -d --rm -p ${port2}:80  nginx"
        //     }
        // }

            // def tasks  = [:] {
            //     tasks["stage1"] = {
            //         stage("stage1"){
            //             sh "docker run --name ${name1} -d --rm -p ${port1}:80  nginx"
            //         }    
            //     }
            //     tasks["stage2"] = {
            //         stage("stage2") {
            //             sh "docker run --name ${name2} -d --rm -p ${port2}:80  nginx"
            //         }    
            //     }

            //     parallel tasks 

            // }
        
            // def tasks = [:]
            // tasks["task_1"] = {
            //     stage ("task_1"){    
            //         node('jenkins') {  
            //             sh  "docker run --name ${name1} -d --rm -p ${port1}:80  nginx"
            //             sleep 30
            //         }
            //     }
            // }
            // tasks["task_2"] = {
            //     stage ("task_2"){    
            //         node('jenkins') {  
            //             sh "docker run --name ${name2} -d --rm -p ${port2}:80  nginx"
            //             sleep 30
            //         }
            //     }
            // }
            // parallel tasks
            
             parallel(
                        "StageA": {
                            stage("stage A") {
                                 sh  "docker run --name ${name1} -d --rm -p ${port1}:80  nginx"
                                 sh " docker inspect ${name1}"
                                 sleep 15
                            }
                        },
                        "StageB": {
                            stage("stage B") {
                                sh "docker run --name ${name2} -d --rm -p ${port2}:80  nginx"
                                 sh " docker inspect ${name2}"
                                sleep 15
                            }
                        }
                )


        stage(name: 'check if the container os running') {
            sh 'docker ps -a'
            sh "curl localhost:${port1} "
            sh "curl localhost:${port2} "
        }
    }
}

return this


// def stage = [:]
// stage("stage1") = {
//     sh "docker run --name ${name1} -d --rm -p ${port1}:80  nginx"
// }
// stage("stage2") = {
//     sh "docker run --name ${name2} -d --rm -p ${port2}:80  nginx"
// }

// parallel stage