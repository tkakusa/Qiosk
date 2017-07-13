# -*- coding: utf-8 -*-
# Generated by Django 1.11.2 on 2017-07-08 17:54
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('server', '0006_auto_20170708_1025'),
    ]

    operations = [
        migrations.AddField(
            model_name='usermodel',
            name='accountBalance',
            field=models.FloatField(default=0),
        ),
        migrations.AlterField(
            model_name='job',
            name='employer',
            field=models.ForeignKey(default=0, on_delete=django.db.models.deletion.CASCADE, related_name='employer', to='server.EmployerModel'),
        ),
        migrations.AlterField(
            model_name='job',
            name='postDate',
            field=models.DateTimeField(auto_now_add=True),
        ),
        migrations.AlterField(
            model_name='job',
            name='startDate',
            field=models.DateTimeField(auto_now_add=True),
        ),
        migrations.AlterField(
            model_name='job',
            name='tags',
            field=models.ManyToManyField(blank=True, to='server.Tag'),
        ),
    ]
