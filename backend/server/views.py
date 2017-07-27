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
    token = request.META['HTTP_AUTHORIZATION']
    if len(request.META['HTTP_AUTHORIZATION']) == 6:
        token = int(token[1:5])
    try:
        if (request.META['HTTP_AUTHORIZATION'] != ''):
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
    if (request.META['HTTP_AUTHORIZATION'] != ''):
        try:
            userModel = UserModel.objects.get(token=request.META['HTTP_AUTHORIZATION'])
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
    validation = validate_data(request)
    print("Validation: ", validation)
    if validation == 1:
        try:
            print(2)
            token = request.META['HTTP_AUTHORIZATION']
            if len(request.META['HTTP_AUTHORIZATION']) == 6:
                token = int(token[1:5])
            userModel = EmployerModel.objects.get(token=token)
        except EmployerModel.DoesNotExist:
            print(3)
            return Response(status=status.HTTP_404_NOT_FOUND)

        userModel.token = ""
        userModel.save()
        return Response(status=status.HTTP_201_CREATED)
        """
        serialized = UserModelSerializer(userModel, data=request.data)
        print(4)
        if serialized.is_valid():
            print(5)
            serialized.save(token="")
            return Response(serialized.data['token'], status=status.HTTP_201_CREATED)
        else:
            print(6)
            print(serialized.errors)
            return Response(serialized.errors, status=status.HTTP_400_BAD_REQUEST)
        """
    else:
        print(7)
        return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def job_detail(request, jobID):
    validation = validate_data(request)
    token = request.META['HTTP_AUTHORIZATION']
    if (len(request.META['HTTP_AUTHORIZATION']) == 6):
        token = int(token[1:5])
    if request.method == 'GET':
        jobs = Job.objects.filter(pk=jobID)
        serializer = JobSerializerEmployer(jobs, many=True)
        return Response(serializer.data)


@api_view(['GET', 'POST', 'OPTIONS'])
def job_list(request):
    validation = validate_data(request)
    print(1)
    token = request.META['HTTP_AUTHORIZATION']
    if (len(request.META['HTTP_AUTHORIZATION']) == 6):
        token = int(token[1:5])
    if validation == 1:
        try:
            print(2)
            this_employer = EmployerModel.objects.get(token=token)
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            print(3)
            jobs = Job.objects.all()
            serializer = JobSerializerEmployer(jobs, many=True)
            return Response(serializer.data)
        elif request.method == 'POST':
            print(4)
            serializer = JobSerializerEmployer(data=request.data)
            if serializer.is_valid():
                print(5)
                serializer.save(employer=this_employer, status="open")
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            else:
                print(serializer._errors)
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
def pending_workers(request, jobID):
    print(1)
    validation = validate_data(request)

    if validation == 1:
        if request.method == 'GET':
            print(4)
            job = Job.objects.get(pk=jobID)
            serializer = JobSerializerEmployer(job)
            return Response(serializer.data["employeesPending"])
    print(5)
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def accepted_workers(request, jobID):
    validation = validate_data(request)
    if validation == 1:
        if request.method == 'GET':
            job = Job.objects.get(pk=jobID)
            serializer = JobSerializerEmployer(job)
            return Response(serializer.data["employeesAccepted"])

    return Response(status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def rate_person(request, userID, rating):
    validation = validate_data(request)
    if request.method == 'POST':
        if validation == 1:
            try:
                rating = int(float(rating))
                employee = UserModel.objects.get(pk=userID)
            except UserModel.DoesNotExist:
                return Response(status=status.HTTP_404_NOT_FOUND)
            employee.rating = ((employee.rating * employee.rating_count) + rating)/(employee.rating_count + 1)
            employee.rating_count = employee.rating_count + 1
            employee.save()
            return Response(status=status.HTTP_201_CREATED)
        elif validation == 2:
            try:
                employer = EmployerModel.objects.get(pk=userID)
            except EmployerModel.DoesNotExist:
                return Response(status=status.HTTP_404_NOT_FOUND)
            employer.rating = ((employer.rating * employer.rating_count) + rating)/(employer.rating_count + 1)
            employer.rating_count = employer.rating_count + 1
            employer.save()
            return Response(status=status.HTTP_201_CREATED)
    return Response(status=status.HTTP_400_BAD_REQUEST)




@api_view(['GET'])
def pending_jobs(request):
    validation = validate_data(request)
    if validation == 2:
        try:
            this_employee = UserModel.objects.get(token=request.META['HTTP_AUTHORIZATION'])
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
            this_employee = UserModel.objects.get(token=request.META['HTTP_AUTHORIZATION'])
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            jobs = Job.objects.filter(employeesAccepted=this_employee)
            serializer = JobSerializerEmployee(jobs, many=True)
            return Response(serializer.data)
    return Response(status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET'])
def completed_jobs(request):
    validation = validate_data(request)
    if validation == 2:
        try:
            this_employee = UserModel.objects.get(token=request.META['HTTP_AUTHORIZATION'])
        except EmployerModel.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'GET':
            jobs = Job.objects.filter(employeesAccepted=this_employee, status="done")
            serializer = JobSerializerEmployee(jobs, many=True)
            return Response(serializer.data)
    return Response(status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def join_job(request):
    validation = validate_data(request)
    if validation == 2:
        try:
            this_employee = UserModel.objects.get(token=request.META['HTTP_AUTHORIZATION'])
            this_job = Job.objects.get(pk=request.data['pk'])
        except EmployerModel.DoesNotExist or Job.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'POST':
            if this_employee not in this_job.employeesPending.all():
                this_job.employeesPending.add(this_employee)
                this_job.save()
            return Response(status=status.HTTP_201_CREATED)
    return Response(status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def accept_employee(request, jobID, workerID):
    print(1)
    validation = validate_data(request)
    print("validation: ", validation)
    print("key", request.META['HTTP_AUTHORIZATION'])
    print(2)
    if validation == 1:
        print(3)
        try:
            this_employee = UserModel.objects.get(pk=workerID)
            this_job = Job.objects.get(pk=jobID)
        except EmployerModel.DoesNotExist or Job.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'POST':
            print(4)
            if this_employee in this_job.employeesPending.all():
                print(5)
                this_job.employeesPending.remove(this_employee)
                if this_employee not in this_job.employeesAccepted.all():
                    print(6)
                    this_job.employeesAccepted.add(this_employee)
                    this_job.status = "in progress"
                    this_job.save()
                    return Response(status=status.HTTP_201_CREATED)
    return Response(status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def close_job(request, jobID):
    validation = validate_data(request)
    if validation == 1:
        print("job ID: ", jobID)
        try:
            jobID = int(float(jobID))
            this_job = Job.objects.get(pk=jobID)
        except EmployerModel.DoesNotExist or Job.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

        if request.method == 'POST':
            workers = this_job.employeesAccepted
            employer = this_job.employer
            pay = this_job.payment
            for worker in workers.all():
                worker.accountBalance +=  pay
                employer.accountBalance -= pay
                worker.save()
                employer.save()
            this_job.status = "done"
            this_job.save()
            return Response(status=status.HTTP_201_CREATED)
    return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
def get_info(request):
    validation = validate_data(request)
    token = request.META['HTTP_AUTHORIZATION']
    print("Token: ", token)
    if (len(request.META['HTTP_AUTHORIZATION']) == 6):
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
