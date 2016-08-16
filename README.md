# Prerequisites  #
## Configure AWS credentials ##
You need to have your AWS access key id and your AWS secret access key.
These keys shall be stored in a file named *credentials* located in the ~/.aws folder.
The content of the file shall be as follows:

````
[default] 
aws_access_key_id = <your_access_key_id>  
aws_secret_access_key = <your_secret_access_key>
````

## Install and configure Docker ##
1. Install [Docker](https://docs.docker.com)
2. Create a [DockerHub](https://hub.docker.com) account
2. Login to dockerhub. 
````
$ docker login
````

## Install AWS CLI ##
[AWS Command Line Interface](https://aws.amazon.com/cli/)

# Create the AWS infrastructure stack #
In order to run our microservice application demo we need an infrastructure stack to run on. We will use AWS Cloudformation to create the stack. The Cloudformation templates to create the stack are available in this repository but they have also been stored in an AWS S3 bucket.

Login to the AWS management console using your username and password. In order to do that you need to know your AWS account id (Ask someone how nows, if you don't know :-))). 
You login [here](https://aws.amazon.com/)  

First we need to create an ECS cluster which will be used to run our application.

1. In the AWS management console, select the *EC2 Container Service* from the *Services* menu

2. Click *Create Cluster*

3. Give your ECS cluster a name and click *Create*

Next we will create the infrastructure stack using Cloudformation. This will create the necessary networking and computing resources that will be need to run the ECS cluster. This includes a VPC, EC2 instances in an auto-scaling group, an Elastic Load Balancer for our service, various security groups and roles needed as well as a [Consul](https://www.consul.io) cluster which is used for service discovery.  

1. Select the *CloudFormation* service

2. Click *Create New Stack*

3. Choose the option *Specify an Amazon S3 template URL* and enter the following URL:
````
https://s3-eu-west-1.amazonaws.com/codecamp2016/cf_appdemo_template.json
````

4. Enter stack parameters
   * Stack name: Use for example your name
   * ClusterName: Use same as for stack name
   * KeyName: Select *realtime*
   * TeamName: Use same as for stack name

5. Click next and then next again

6.  Tick the checkbox *I acknowledge that AWS CloudFormation might create IAM resources* and then click *Create*

7. This will now take a while, so go grab some coffee. You can follow the progress by selecting the *Events* tab at the bottom of the CloudFormation page.  


# Running the demo app #
## Adapting the application AWS scripts ##
The first thing we need to do is to adapt the JSON scripts that will be used to create AWS ECS task defintions and services for our application, so that these are created in the ECS cluster that we created earlier.

1. Locate the file *customerservice/src/main/resources/aws-ecs/taskdefinition.json*. 
Replace *<your_dockerhub_name>* with your DockerHub username and replace *<TeamName>* with the team name you entered when creating the infrastructure stack.

2. Locate the file *customerservice/src/main/resources/aws-ecs/servicedefinition.json*. Replace *<TeamName>* with the team name you entered when creating the infrastructure stack.  

3. Do the same thing for the files *orderservice/src/main/resources/aws-ecs/taskdefinition.json* and *orderservice/src/main/resources/aws-ecs/servicedefinition.json*

# Building and launching #
1. First go to the logutil module
2. Build the artifact: 
````
$ mvn -s ../settings.xml clean install
````

## Building and launching the Customer service ##
1. Go to the customerservice module
2. Build the artifact: 
````
$ mvn -s ../settings.xml clean install
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
$ aws ecs register-task-definition --cli-input-json file://src/main/resources/aws-ecs/taskdefinition.json
````
6. Create the service in AWS ECS 
````
$ aws ecs create-service --cluster <your_cluster_name> --service-name CustomerService  --cli-input-json file://src/main/resources/aws-ecs/servicedefinition.json
````

## Building and launching the Order service ##
1. Go to the orderservice module
2. Build the artifact:
````
$ mvn -s ../settings.xml clean install
````
3. Build the docker image
````
$ docker build -t <your_dockerhub_name>/orderservice:1 .
````
4. Push the image
````
$ docker push <your_dockerhub_name>/orderservice:1
````
5. Register a task definition for the docker image in AWS ECS. Will we do this using the AWS CLI.
````
$ aws ecs register-task-definition --cli-input-json file://src/main/resources/aws-ecs/taskdefinition.json
````
6. Create the service in AWS ECS
````
$ aws ecs create-service --cluster <your_cluster_name> --service-name OrderService  --cli-input-json file://src/main/resources/aws-ecs/servicedefinition.json
````
## Verifying that the application is running ##
First we check that ECS cluster is correctly launched.
1. Log in to the AWS console
2. Select *EC2 Container Service* in the *Services* menu
3. Select your cluster
4. Verify that the CustomerService and the OrderService exist
5. Select the OrderService and verify that there are a task running for the service. Do the same for the CustomerService

Now check that the load balancer
1. Select *EC2* in the *Services* memu
2. Click *Load Balancers*
3. Select your load balancer
4. Click on the *Instances* tab (in the bottom of the page)
5. Verify that there is at least on instance that have status *InService*
6. Click on the *Description* tab and copy the DNS name for the load balancer.

Now we will test to access the application.
From a command prompt run the following curl command. Replease <your_loadbalancer_dns_name> with the DNS name you copied above.
````
$ curl -v -d '{"productId":12,"quantity":1}' -H 'Content-Type:application/json' -H 'Accept:application/json'  Kalle:pw@&lt;your_loadbalancer_dns_name&gt;/order
````


 
