from rest_framework import serializers
from server.models import UserModel, EmployerModel, Tag, Job


class UserModelSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserModel
        fields = (
        'pk', 'username', 'password', 'accountBalance', 'rating', 'jobsDone', 'token', 'firstName', 'middleName', 'lastName',
        'phoneNumber', 'address', 'email')


class UserModelLoginSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserModel
        fields = ('username', 'password', 'token')


class EmployerModelSerializer(serializers.ModelSerializer):
    class Meta:
        model = EmployerModel
        fields = ('pk', 'username', 'password', 'accountBalance', 'token', 'firstName', 'middleName', 'lastName', 'phoneNumber', 'address')

class EmployerInfoModelSerializer(serializers.ModelSerializer):
    class Meta:
        model = EmployerModel
        fields = ('firstName', 'lastName')

class TagSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tag
        fields = ('pk', 'tagName', 'iconName', 'description')


class JobSerializerEmployer(serializers.ModelSerializer):
    employeesPending = UserModelSerializer(read_only=True, many=True)
    employeesAccepted = UserModelSerializer(read_only=True, many=True)

    class Meta:
        model = Job
        fields = (
        'pk', 'title', 'employer', 'employeesPending', 'employeesAccepted', 'payment', 'description', 'startDate',
        'postDate', 'numberPeopleNeeded', 'numberPeopleAccepted', 'status', 'address')


class JobSerializerEmployee(serializers.ModelSerializer):
    employer = EmployerInfoModelSerializer(read_only=True)

    class Meta:
        model = Job
        fields = (
        'pk', 'title', 'tags', 'employer', 'payment', 'description', 'startDate', 'postDate', 'numberPeopleNeeded',
        'numberPeopleAccepted', 'status', 'address')


class JobJoinSerializer(serializers.ModelSerializer):
    class Meta:
        model = Job
        fields = ('pk', 'employeesPending')
