from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from rest_framework import status
from server.models import UserModel, EmployerModel, Tag, Job
from server.serializers import UserModelLoginSerializer, UserModelSerializer, EmployerModelSerializer, TagSerializer, JobJoinSerializer, JobSerializerEmployer, JobSerializerEmployee
from rest_framework.authtoken.models import Token
from random import randrange

def validate_data(request):
	try:
		if (request.META['HTTP_TOKEN'] != ''):
			if (EmployerModel.objects.filter(token=request.META['HTTP_TOKEN']).count() and UserModel.objects.filter(token=request.META['HTTP_TOKEN']).count()):
				return 3
			elif UserModel.objects.filter(token=request.META['HTTP_TOKEN']).count():
				return 2
			elif EmployerModel.objects.filter(token=request.META['HTTP_TOKEN']).count():
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
        serialized.save()
        return Response(serialized.data, status=status.HTTP_201_CREATED)
    else:
        return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def create_employer(request):
    serialized = EmployerModelSerializer(data=request.data)
    if serialized.is_valid():
        serialized.save()
        return Response(serialized.data, status=status.HTTP_201_CREATED)
    else:
        return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def get_key_user(request):
	print(request.data)
	try:
		userModel = UserModel.objects.get(username=request.data['username'], password=request.data['password'])
	except UserModel.DoesNotExist:
		return Response(status=status.HTTP_404_NOT_FOUND)

	serialized = UserModelLoginSerializer(userModel, data=request.data)
	if serialized.is_valid():
		new_token = randrange(1000, 9999)
		while  EmployerModel.objects.filter(token=new_token).count() or UserModel.objects.filter(token=new_token).count():
			new_token = randrange(1000, 9999)
		serialized.save(token=new_token)
		return Response(serialized.data['token'], status=status.HTTP_201_CREATED)
	else:
		return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)
	
@api_view(['POST'])
def get_key_employer(request):
	try:
		userModel = EmployerModel.objects.get(username=request.data['username'], password=request.data['password'])
	except UserModel.DoesNotExist:
		return Response(status=status.HTTP_404_NOT_FOUND)

	serialized = UserModelLoginSerializer(userModel, data=request.data)
	if serialized.is_valid():
		new_token = randrange(1000, 9999)
		while  EmployerModel.objects.filter(token=new_token).count() or UserModel.objects.filter(token=new_token).count():
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
		return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)
@api_view(['DELETE'])
def remove_key_employer(request):
	if (request.data['token'] != ''):
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
		return Response(serialized._errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'POST'])
def job_list(request):
	validation = validate_data(request)
	if validation == 1:
		try:
			this_employer = EmployerModel.objects.get(token=request.META['HTTP_TOKEN'])
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
	else:
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
				this_job.employeesPending.add(this_employee)
				this_job.save()
			return Response(status=status.HTTP_201_CREATED)
	return Response(status=status.HTTP_400_BAD_REQUEST)
			
	
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
	
