pipeline {
	agent { dockerfile true }
	stages {
		stage('Test') {
			steps {
				sh 'mvn package'
				sh  'echo "backend"'
			}
		}
	}
}
