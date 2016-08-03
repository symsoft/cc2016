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
2. Login to dockerhub. 
````
$ docker login
````

## Install AWS CLI ##
[AWS Command Line Interface](https://aws.amazon.com/cli/)

# Create the AWS infrastructure stack #
In order to run our microservice application demo we need an infrastructure stack to run on. We will use AWS Cloudformation to create the stack. The Cloudformation templates to create the stack are available in this repository but they have also been stored in an AWS S3 bucket.

1. First login to the AWS management console using your username and password. In order to do that you need to know the Symsoft's AWS account id (Ask someone how nows, if you don't know :-))). 
You login [here](https://aws.amazon.com/)  

2. Select the *EC2 Container Service*

3. Click *Create Cluster*

4. Give your ECS cluster a name and click *Create*

5. Next we will create the infrastructure stack using Cloudformation. Select the *CloudFormation* service

6. Click *Create Stack*

7. Choose the option *Specify an Amazon S3 template URL* and enter the following URL:
````
https://s3-eu-west-1.amazonaws.com/codecamp2016/cf_appdemo_template.json
````

8. Enter stack parameters
   * Stack name: Use for example your name
   * ClusterName: Use same as for stack name
   * KeyName: Select *realtime*
   * TeamName: Use same as for stack name

9. Click next and then next again

10. Tick the checkbox *I acknowledge that AWS CloudFormation might create IAM resources* and then click *Create*

11. This will now take a while, so go grab some coffee.  

   

# Running the demo app #
## Adapting the application AWS scripts ##
The first thing we need to do is to adapt some JSON scripts so that application launches in the ECS cluster that we created earlier.

1. Locate the file *customerservice/main/resources/aws-ecs/taskdefinition.json*. 
Replace *<your_dockerhub_name>* with your DockerHub username and replace *<TeamName>* with the team name you entered when creating the infrastructure stack.

2. Locate the file *customerservice/main/resources/aws-ecs/servicedefinition.json*. Replace *<TeamName>* with the team name you entered when creating the infrastructure stack.  

3. Do the same thing for the files *orderservice/main/resources/aws-ecs/taskdefinition.json* and *orderservice/main/resources/aws-ecs/servicedefinition.json*

## Building and launching the Customer service ##
1. Go to the customerservice module
2. Build the artifact: 
````
$ mvn clean install
````
3. Build the docker image
````
$ docker build -t <your_dockerhub_name>/customerservice:1 .
````
4. Push the image
````
$ docker push <your_dockerhub_name>/customerservice:1
````
5. Register a task definition for the docker image in AWS ECS. Will we do this using the AWS CLI.
````
`$ aws ecs register-task-definition --cli-input-json file://main/src/resources/aws-ecs/taskdefinition.json
````
6. Create the service in AWS ECS 
````
aws ecs create-service --cluster <your_cluster_name> --service-name CustomerService  --cli-input-json file://main/src/resources/aws-ecs/servicedefinition.json
````
