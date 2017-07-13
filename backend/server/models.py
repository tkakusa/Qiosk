from django.db import models
from django.conf import settings
from django.db.models.signals import post_save
from django.dispatch import receiver
from rest_framework.authtoken.models import Token

@receiver(post_save, sender=settings.AUTH_USER_MODEL)
def create_auth_token(sender, instance=None, created=False, **kwargs):
    if created:
        Token.objects.create(user=instance)

class UserModel(models.Model):
	username = models.CharField(max_length=200)
	password = models.CharField(max_length=200)
	token = models.CharField(max_length=200, blank=True, default='')
	firstName = models.CharField(max_length=200)
	middleName = models.CharField(max_length=200, blank=True, default='')
	lastName = models.CharField(max_length=200)
	phoneNumber = models.IntegerField(default=0)
	address = models.CharField(max_length=200, blank=True, default='')
	accountBalance = models.FloatField(default=0)
	jobsDone = models.IntegerField(default=0)
	rating = models.IntegerField(default=0)
	class Meta:
		ordering = ('lastName',)

class EmployerModel(models.Model):
	username = models.CharField(max_length=200)
	password = models.CharField(max_length=200)
	token = models.CharField(max_length=200, blank=True, default='')
	firstName = models.CharField(max_length=200)
	middleName = models.CharField(max_length=200, blank=True, default='')
	lastName = models.CharField(max_length=200)
	phoneNumber = models.IntegerField(default=0)
	address = models.CharField(max_length=200)
	
	class Meta:
		ordering = ('lastName',)
class Tag(models.Model):
	tagName = models.CharField(max_length=200)
	iconName = models.CharField(max_length=200)
	description = models.CharField(max_length=200, blank=True, default='')
	
	class Meta:
		ordering = ('tagName',)
	
	
class Job(models.Model):
	tags = models.ManyToManyField(Tag, blank=True)
	employer = models.ForeignKey(EmployerModel, related_name='employer', on_delete=models.CASCADE, default=0)
	employeesPending = models.ManyToManyField(UserModel, related_name='employeesPending', blank=True)
	employeesAccepted = models.ManyToManyField(UserModel, related_name='employeesAccepted', blank=True)
	payment = models.IntegerField(default=0)
	title = models.CharField(max_length=500)
	description = models.CharField(max_length=500)
	startDate = models.DateTimeField(auto_now_add=True)
	duration = models.IntegerField(default=0)
	postDate = models.DateTimeField(auto_now_add=True)
	numberPeopleNeeded = models.IntegerField(default=0)
	numberPeopleAccepted = models.IntegerField(default=0)
	status = models.CharField(max_length=10, blank=True, default='')
	posted = models.BooleanField(default=False, blank=True)
	address = models.CharField(max_length=500)
	
	class Meta:
		ordering = ('startDate',)
