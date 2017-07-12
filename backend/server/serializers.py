from rest_framework import serializers
from server.models import UserModel, EmployerModel, Tag, Job

class UserModelSerializer(serializers.ModelSerializer):
	
	class Meta:
		model = UserModel
		fields = ('username', 'password', 'token', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'address')

class UserModelLoginSerializer(serializers.ModelSerializer):
	class Meta:
		model = UserModel
		fields = ('username', 'password', 'token')

	
class EmployerModelSerializer(serializers.ModelSerializer):
	
	class Meta:
		model = EmployerModel
		fields = ('username', 'password', 'token', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'address')
				
class TagSerializer(serializers.ModelSerializer):
	class Meta:
		model = Tag
		fields = ('pk', 'tagName', 'iconName', 'description')

class JobSerializerEmployer(serializers.ModelSerializer):
	class Meta:
		model = Job
		fields = ('pk', 'tags', 'employer', 'employeesPending', 'employeesAccepted', 'payment', 'description', 'startDate', 'postDate', 'numberPeopleNeeded', 'numberPeopleAccepted', 'status', 'address')

class JobSerializerEmployee(serializers.ModelSerializer):
	class Meta:
		model = Job
		fields = ('pk', 'tags', 'employer', 'payment', 'description', 'startDate', 'postDate', 'numberPeopleNeeded', 'numberPeopleAccepted', 'status', 'address')

class JobJoinSerializer(serializers.ModelSerializer):
	class Meta:
		model = Job
		fields = ('pk', 'employeesPending')