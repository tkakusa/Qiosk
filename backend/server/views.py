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
    # print("Length: ", len(token))
    print("Token: ", token)
    try:
        print("Enter 0")
        if (request.META['HTTP_TOKEN'] != ''):
            print("Enter 1")
            if (EmployerModel.objects.filter(token=token).count() and UserModel.objects.filter(token=token).count()):
                print("Enter 2")
                return 3
            elif UserModel.objects.filter(token=token).count():
                print("Enter 3")
                return 2
            elif EmployerModel.objects.filter(token=token).count():
                print("Enter 4")
                return 1
            else:
                print("Enter 5")
                return 0
        else:
            print("Enter 6")
            return 0
    except:
        return 0


def validate_data2(token):
    # print("Length: ", len(token))
    print("Token: ", token)
    try:

        print("Enter 0")
        if (token != ''):
            print("Enter 1")
            if (EmployerModel.objects.filter(token=token).count() and UserModel.objects.filter(token=token).count()):
                print("Enter 2")
                return 3
            elif UserModel.objects.filter(token=token).count():
                print("Enter 3")
                return 2
            elif EmployerModel.objects.filter(token=token).count():
                print("Enter 4")
                return 1
            else:
                print("Enter 5")
                return 0
        else:
            print("Enter 6")
            return 0
    except:
        return 0


@api_view(['POST'])
def create_user(request):
    serialized = UserModelSerializer(data=request.data)
    print(request.data)
    if serialized.is_valid():
        new_token = randrange(1000, 9999)
        while EmployerModel.objects.filter(token=new_token).count() or UserModel.objects.filter(
                token=new_token).count():
            new_token = randrange(1000, 9999)
        serialized.save(token=new_token, rating=randrange(100, 500), accountBalance=0, jobsDone=randrange(0, 200))
        print(serialized.data['token'])
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
    print(1)
    print(request.data)
    try:
        print(2)
        userModel = UserModel.objects.get(username=request.data['username'], password=request.data['password'])
    except UserModel.DoesNotExist:
        print(3)
        return Response(status=status.HTTP_404_NOT_FOUND)

    serialized = UserModelLoginSerializer(userModel, data=request.data)
    print(4)
    if serialized.is_valid():
        print(5)
        new_token = randrange(1000, 9999)
        print(7)
        while EmployerModel.objects.filter(token=new_token).count() or UserModel.objects.filter(
                token=new_token).count():
            print(6)
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
    print(request.META)
    validation = validate_data(request)
    token = request.META['HTTP_TOKEN']
    if (len(request.META['HTTP_TOKEN']) == 6):
        token = int(token[1:5])
    if validation == 1:
        try:
            print(1)
            this_employer = EmployerModel.objects.get(token=token)
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            jobs = Job.objects.filter(employer=this_employer)
            serializer = JobSerializerEmployer(jobs, many=True)
            return Response(serializer.data)
        elif request.method == 'POST':
            print(2)
            serializer = JobSerializerEmployer(data=request.data)
            if serializer.is_valid():
                print(3)
                serializer.save(employer=this_employer, status="open")
                print(4)
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            else:
                print(5)
                return Response(serializer._errors, status=status.HTTP_400_BAD_REQUEST)
    elif validation == 2:
        if request.method == 'GET':
            jobs = Job.objects.all()
            serializer = JobSerializerEmployee(jobs, many=True)
            return Response(serializer.data)
    if request.method == 'OPTIONS':
        return Response("good")
    print(6)
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'POST', 'OPTIONS'])
def job_list2(request, token):
    print(request.META)
    validation = validate_data2(token)
    if validation == 1:
        try:
            print(1)
            this_employer = EmployerModel.objects.get(token=token)
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            jobs = Job.objects.filter(employer=this_employer)
            serializer = JobSerializerEmployer(jobs, many=True)
            return Response(serializer.data)
        elif request.method == 'POST':
            print(2)
            serializer = JobSerializerEmployer(data=request.data)
            if serializer.is_valid():
                print(3)
                serializer.save(employer=this_employer, status="open")
                print(4)
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            else:
                print(5)
                return Response(serializer._errors, status=status.HTTP_400_BAD_REQUEST)
    elif validation == 2:
        if request.method == 'GET':
            jobs = Job.objects.all()
            serializer = JobSerializerEmployee(jobs, many=True)
            return Response(serializer.data)
    if request.method == 'OPTIONS':
        return Response("good")
    print(6)
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
    print("Validation: ", validation)
    if validation == 2:
        print("Entered Here")
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
    print("Validation: ", validation)
    if validation == 2:
        print("Entered Here")
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
    print(request.META['HTTP_TOKEN'])
    print("Validation: ", validation)
    if validation is 1:
        try:
            employer = EmployerModel.objects.get(token=token)
        except:
            Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            serializer = EmployerModelSerializer(employer)
            return Response(serializer.data)
    elif validation is 2:
        print("entered")
        try:
            employee = UserModel.objects.get(token=token)
            print("entered 2")
        except:
            Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            print("entered 3")
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
        print("entered")
        employee = UserModel.objects.get(token=token)
        try:

            print("entered 2")
            if request.method == 'GET':
                print("entered 3")
                serializer = UserModelSerializer(employee)
                return Response(serializer.data)
        except:
            Response(status=status.HTTP_404_NOT_FOUND)

    return Response(status=status.HTTP_404_NOT_FOUND)


@api_view(['GET', 'POST'])
def tag_list(request):
    validation = validate_data(request)
    print(0)
    if validation:
        print(2)
        if request.method == 'GET':
            tags = Tag.objects.all()
            serializer = TagSerializer(tags, many=True)
            return Response(serializer.data)
        elif request.method == 'POST':
            print(1)
            print()
            serializer = TagSerializer(data=request.data)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response(status=status.HTTP_400_BAD_REQUEST)
