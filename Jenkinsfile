node ('beaware-jenkins-slave') {
    stage('Download Latest') {
        git(url: 'https://github.com/beaware-project/report-generation.git', branch: 'master')
        sh 'git submodule init'
        sh 'git submodule update'
    }

    stage ('Compile (Maven)') {
        //sh 'mvn clean package -U'
       	sh 'echo skipping mvn compile'
    }

    stage ('Build docker image') {
		//sh 'docker build -t beaware/report-generation:${BUILD_NUMBER} .'
		sh 'echo skipping docker build'
    }

    stage ('Push docker image') {
        withDockerRegistry([credentialsId: 'dockerhub-credentials']) {
            //sh 'docker push beaware/report-generation:${BUILD_NUMBER}'
            sh 'echo skipping docker push'
        }
    }

    stage ('Deploy') {
 		sh ''' sed -i 's/IMAGE_TAG/'"$BUILD_NUMBER"'/g' kubernetes/deploy.yaml '''
        sh 'kubectl apply -f kubernetes/deploy.yaml -n prod --validate=false'
    }
    
    stage ('Print-deploy logs') {
        sh 'sleep 60'
        sh 'kubectl  -n prod logs deploy/report-generation -c report-generation'
    }    
}