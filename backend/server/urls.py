from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from server import views

urlpatterns = [
    url(r'^createUser/$', views.create_user),
    url(r'^createEmployer/$', views.create_employer),
    url(r'^loginUser/$', views.get_key_user),
    url(r'^loginEmployer/$', views.get_key_employer),
    url(r'^logoutUser/$', views.remove_key_user),
    url(r'^logoutEmployer/$', views.remove_key_employer),
    url(r'^list/$', views.job_list),
    url(r'^list/jobID=(?P<jobID>[0-9]+)/$', views.job_detail),
    url(r'^tags/$', views.tag_list),
    url(r'^pendingWorkers/jobID=(?P<jobID>[0-9]+)/$', views.pending_workers),
    url(r'^acceptedWorkers/jobID=(?P<jobID>[0-9]+)/$', views.accepted_workers),
    url(r'^pendingJobs/$', views.pending_jobs),
    url(r'^acceptedJobs/$', views.accepted_jobs),
    url(r'^completedJobs/$', views.completed_jobs),
    url(r'^joinJob/$', views.join_job),
    url(r'^acceptEmployee/jobID=(?P<jobID>[0-9]+)/workerID=(?P<workerID>[0-9]+)/$', views.accept_employee),
    url(r'^profile/$', views.get_info),
    url(r'^closeJob/jobID=(?P<jobID>[0-9]+)/$', views.close_job),
    url(r'^rate/userID=(?P<userID>[0-9]+)/rating=(?P<rating>[0-9]+)/$', views.rate_person),
]

urlpatterns = format_suffix_patterns(urlpatterns)
