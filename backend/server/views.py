from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from rest_framework import status
from server.models import UserModel, EmployerModel, Tag, Job
from server.serializers import UserModelLoginSerializer, UserModelSerializer, EmployerModelSerializer, TagSerializer, \
    JobJoinSerializer, JobSerializerEmployer, JobSerializerEmployee
from rest_framework.authtoken.models import Token
from random import randrange
import time


def validate_data(request):
    token = request.META['HTTP_TOKEN']
    if len(request.META['HTTP_TOKEN']) == 6:
        token = int(token[1:5])
    try:
        if (request.META['HTTP_TOKEN'] != ''):
            if (EmployerModel.objects.filter(token=token).count() and UserModel.objects.filter(token=token).count()):
                return 3
            elif UserModel.objects.filter(token=token).count():
                return 2
            elif EmployerModel.objects.filter(token=token).count():
                return 1
            else:
                return 0
        else:
            return 0
    except:
        return 0


def validate_data2(token):
    try:
        if (token != ''):
            if (EmployerModel.objects.filter(token=token).count() and UserModel.objects.filter(token=token).count()):
                return 3
            elif UserModel.objects.filter(token=token).count():
                return 2
            elif EmployerModel.objects.filter(token=token).count():
                return 1
            else:
                return 0
        else:
            return 0
    except:
        return 0


@api_view(['POST'])
def create_user(request):
    serialized = UserModelSerializer(data=request.data)
    if serialized.is_valid():
        new_token = randrange(1000, 9999)
        while EmployerModel.objects.filter(token=new_token).count() or UserModel.objects.filter(
                token=new_token).count():
            new_token = randrange(1000, 9999)
        serialized.save(token=new_token, rating=randrange(100, 500), accountBalance=0, jobsDone=randrange(0, 200))
        return Response(serialized.data['token'], status=status.HTTP_201_CREATED)
    else:
        return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def create_employer(request):
    serialized = EmployerModelSerializer(data=request.data)
    if serialized.is_valid():
        new_token = randrange(1000, 9999)
        while EmployerModel.objects.filter(token=new_token).count() or UserModel.objects.filter(
                token=new_token).count():
            new_token = randrange(1000, 9999)
        serialized.save(token=new_token)
        return Response(serialized.data['token'], status=status.HTTP_201_CREATED)
    else:
        return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def get_key_user(request):
    try:
        userModel = UserModel.objects.get(username=request.data['username'], password=request.data['password'])
    except UserModel.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serialized = UserModelLoginSerializer(userModel, data=request.data)
    if serialized.is_valid():
        new_token = randrange(1000, 9999)
        while EmployerModel.objects.filter(token=new_token).count() or UserModel.objects.filter(token=new_token).count():
            new_token = randrange(1000, 9999)
        serialized.save(token=new_token)
        return Response(serialized.data['token'], status=status.HTTP_201_CREATED)
    else:
        return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def get_key_employer(request):
    try:
        userModel = EmployerModel.objects.get(username=request.data['username'], password=request.data['password'])
    except EmployerModel.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    serialized = UserModelLoginSerializer(userModel, data=request.data)
    if serialized.is_valid():
        new_token = randrange(1000, 9999)
        while EmployerModel.objects.filter(token=new_token).count() or UserModel.objects.filter(
                token=new_token).count():
            new_token = randrange(1000, 9999)
        serialized.save(token=new_token)
        return Response(serialized.data['token'], status=status.HTTP_201_CREATED)
    else:
        return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['DELETE'])
def remove_key_user(request):
    if (request.META['HTTP_TOKEN'] != ''):
        try:
            userModel = UserModel.objects.get(token=request.META['HTTP_TOKEN'])
        except UserModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        serialized = UserModelLoginSerializer(userModel, data=request.data)
        if serialized.is_valid():
            serialized.save(token="")
            return Response(serialized.data['token'], status=status.HTTP_201_CREATED)
        else:
            return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['DELETE'])
def remove_key_employer(request):
    if request.data['token'] != '':
        try:
            userModel = EmployerModel.objects.get(token=request.META['HTTP_TOKEN'])
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        serialized = UserModelLoginSerializer(userModel, data=request.data)
        if serialized.is_valid():
            serialized.save(token="")
            return Response(serialized.data['token'], status=status.HTTP_201_CREATED)
        else:
            return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def job_detail(request, pk):
    validation = validate_data(request)
    token = request.META['HTTP_TOKEN']
    if (len(request.META['HTTP_TOKEN']) == 6):
        token = int(token[1:5])
    if request.method == 'GET':
        jobs = Job.objects.filter(pk=pk)
        serializer = JobSerializerEmployer(jobs, many=True)
        return Response(serializer.data)


@api_view(['GET', 'POST', 'OPTIONS'])
def job_list(request):
    validation = validate_data(request)
    token = request.META['HTTP_TOKEN']
    if (len(request.META['HTTP_TOKEN']) == 6):
        token = int(token[1:5])
    if validation == 1:
        try:
            this_employer = EmployerModel.objects.get(token=token)
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            jobs = Job.objects.filter(employer=this_employer)
            serializer = JobSerializerEmployer(jobs, many=True)
            return Response(serializer.data)
        elif request.method == 'POST':
            serializer = JobSerializerEmployer(data=request.data)
            if serializer.is_valid():
                serializer.save(employer=this_employer, status="open")
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            else:
                return Response(serializer._errors, status=status.HTTP_400_BAD_REQUEST)
    elif validation == 2:
        if request.method == 'GET':
            jobs = Job.objects.all()
            serializer = JobSerializerEmployee(jobs, many=True)
            return Response(serializer.data)
    if request.method == 'OPTIONS':
        return Response("good")
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'POST', 'OPTIONS'])
def job_list2(request, token):
    validation = validate_data2(token)
    if validation == 1:
        try:
            this_employer = EmployerModel.objects.get(token=token)
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            jobs = Job.objects.filter(employer=this_employer)
            serializer = JobSerializerEmployer(jobs, many=True)
            return Response(serializer.data)
        elif request.method == 'POST':
            serializer = JobSerializerEmployer(data=request.data)
            if serializer.is_valid():
                serializer.save(employer=this_employer, status="open")
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            else:
                return Response(serializer._errors, status=status.HTTP_400_BAD_REQUEST)
    elif validation == 2:
        if request.method == 'GET':
            jobs = Job.objects.all()
            serializer = JobSerializerEmployee(jobs, many=True)
            return Response(serializer.data)
    if request.method == 'OPTIONS':
        return Response("good")
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def pending_jobs(request):
    validation = validate_data(request)
    if validation == 2:
        try:
            this_employee = UserModel.objects.get(token=request.META['HTTP_TOKEN'])
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            jobs = Job.objects.filter(employeesPending=this_employee)
            serializer = JobSerializerEmployee(jobs, many=True)
            return Response(serializer.data)
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def accepted_jobs(request):
    validation = validate_data(request)
    if validation == 2:
        try:
            this_employee = UserModel.objects.get(token=request.META['HTTP_TOKEN'])
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            jobs = Job.objects.filter(employeesAccepted=this_employee)
            serializer = JobSerializerEmployee(jobs, many=True)
            return Response(serializer.data)
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def join_job(request):
    validation = validate_data(request)
    if validation == 2:
        try:
            this_employee = UserModel.objects.get(token=request.META['HTTP_TOKEN'])
            this_job = Job.objects.get(pk=request.data['pk'])
        except EmployerModel.DoesNotExist or Job.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'POST':
            if this_employee not in this_job.employeesPending.all():
                this_job.employeesAccepted.add(this_employee)
                this_job.save()
            return Response(status=status.HTTP_201_CREATED)
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def join_job2(request, token):
    validation = validate_data2(token)
    if validation == 2:
        try:
            this_employee = UserModel.objects.get(token=token)
            this_job = Job.objects.get(pk=request.data['pk'])
        except EmployerModel.DoesNotExist or Job.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'POST':
            if this_employee not in this_job.employeesPending.all():
                this_job.employeesAccepted.add(this_employee)
                this_job.save()
            return Response(status=status.HTTP_201_CREATED)
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def close_job(request):
    pk = request.data['pk']
    if pk[0] == "\"":
        pk = int(pk[1:-1])
    validation = validate_data(request)
    if validation == 1:
        try:
            this_job = Job.objects.get(pk=pk)
        except EmployerModel.DoesNotExist or Job.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'POST':
            this_job.status = "Complete"
            this_job.save()
            return Response(status=status.HTTP_200)
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def get_info(request):
    validation = validate_data(request)
    token = request.META['HTTP_TOKEN']
    if (len(request.META['HTTP_TOKEN']) == 6):
        token = int(token[1:5])
    if validation is 1:
        try:
            employer = EmployerModel.objects.get(token=token)
        except:
            Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            serializer = EmployerModelSerializer(employer)
            print(serializer.data)
            return Response(serializer.data)
    elif validation is 2:
        try:
            employee = UserModel.objects.get(token=token)
        except:
            Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            serializer = UserModelSerializer(employee)
            return Response(serializer.data)
    return Response(status=status.HTTP_404_NOT_FOUND)


@api_view(['GET'])
def get_info2(request, token):
    validation = validate_data2(token)
    if validation is 1:
        try:
            employer = EmployerModel.objects.get(token=token)
        except:
            Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            serializer = EmployerModelSerializer(employer)
            return Response(serializer.data)
    elif validation is 2:
        employee = UserModel.objects.get(token=token)
        try:
            if request.method == 'GET':
                serializer = UserModelSerializer(employee)
                return Response(serializer.data)
        except:
            Response(status=status.HTTP_404_NOT_FOUND)

    return Response(status=status.HTTP_404_NOT_FOUND)


@api_view(['GET', 'POST'])
def tag_list(request):
    validation = validate_data(request)
    if validation:
        if request.method == 'GET':
            tags = Tag.objects.all()
            serializer = TagSerializer(tags, many=True)
            return Response(serializer.data)
        elif request.method == 'POST':
            serializer = TagSerializer(data=request.data)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response(status=status.HTTP_400_BAD_REQUEST)
