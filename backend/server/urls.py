from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from server import views

urlpatterns = [
    url(r'^createUser/$', views.create_user),
	url(r'^loginUser/$', views.get_key_user),
    url(r'^createEmployer/$', views.create_employer),
	url(r'^loginEmployer/$', views.get_key_employer),
	url(r'^logoutUser/$', views.remove_key_user),
	url(r'^logoutEmployer/$', views.remove_key_employer),
	url(r'^list/$', views.job_list),
	url(r'^tags/$', views.tag_list),
	url(r'^pendingJobs/$', views.pending_jobs),
	url(r'^acceptedJobs/$', views.accepted_jobs),
	url(r'^joinJob/$', views.join_job),
]

urlpatterns = format_suffix_patterns(urlpatterns)