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
    url(r'^list/(?P<token>[0-9]+)/$', views.job_list2),
    url(r'^list/pk=(?P<pk>[0-9]+)/$', views.job_detail),
    url(r'^tags/$', views.tag_list),
    url(r'^pendingJobs/$', views.pending_jobs),
    url(r'^acceptedJobs/$', views.accepted_jobs),
    url(r'^joinJob/$', views.join_job),
    url(r'^joinJob/(?P<token>[0-9]+)/$', views.join_job2),
    url(r'^profile/$', views.get_info),
    url(r'^profile/(?P<token>[0-9]+)/$', views.get_info2),
    url(r'^closeJob/$', views.close_job),
]

urlpatterns = format_suffix_patterns(urlpatterns)
