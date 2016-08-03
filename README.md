# Prerequisites  #
## Configure AWS credentials ##
You need to have you AWS access key id and your AWS secret access key.
These keys shall be stored in a filed named *credentials* located in the ~/.aws folder.
The content of the file shall be as follows:

````
  [default]  
  aws_access_key_id = <your_access_key_id>  
  aws_secret_access_key = <your_secret_access_key>
````

## Install and configure Docker ##
1. Install [Docker](https://docs.docker.com)
2. Login to dockerhub. `docker login`

## Install AWS CLI ##
[AWS Command Line Interface](https://aws.amazon.com/cli/)


# Running the demo app #
## Building and launching the Customer service ##
1. Go to the customerservice module
2. Build the artifact: *mvn clean install*
3. Build the docker image: *docker build -t <your_dockerhub_name>/customerservice:1 .*
4. Push the image: *docker push <your_dockerhub_name>/customerservice:1*
5. aws ecs register-task-definition --cli-input-json file://taskdefinition.json
6. aws ecs create-service --cluster Kalle --service-name CustomerService  --cli-input-json file://servicedefinition.json

