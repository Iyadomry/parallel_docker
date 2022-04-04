def main() {
    ansiColor('xterm') {
        docker1() 
    }
}

def docker1() {
    node(label: 'jenkins') {
        properties(
        properties([parameters([string(defaultValue: 'app3', name: 'name1'), string(defaultValue: 'app4', name: 'name2'), string(defaultValue: '58', name: 'port1'), string(defaultValue: '59', name: 'port2')])])
        )
        try {
            stage('remove all running containers') {
                sh 'docker rm -f $(docker ps -a -q)'
                }
            }
        catch (Exception) {
            echo ' There is no current containers to delete, try again '
            currentBuild.result = 'SUCCESS'
            }

        stage('Deply my application as a container') {
            ansiColor('xterm') {
                echo "\u001B[31mbuild new container\u001B[0m"
                sh "docker run --name ${name1} -d --rm -p ${port1}:80  nginx"
                sh "docker run --name ${name2} -d --rm -p ${port2}:80  nginx"
            }
        }
        stage(name: 'check if the container os running') {
            sh 'docker ps -a'
            sh "curl localhost:${port1} "
            sh "curl localhost:${port2} "
        }
    }
}

return this