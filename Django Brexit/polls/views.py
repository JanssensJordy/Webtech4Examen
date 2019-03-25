from django.shortcuts import render

# Create your views here.
from django.http import HttpResponse
import string
import datetime

def strfdelta(tdelta, fmt):
    d = {"days": tdelta.days}
    d["hours"], rem = divmod(tdelta.seconds, 3600)
    d["minutes"], d["seconds"] = divmod(rem, 60)
    d["hours"] += (tdelta.days*24)
    return fmt.format(**d)

def index(request):
        brexit = datetime.datetime(2019,3,29)
        now = datetime.datetime.now()
        time_till_brexit = brexit - now
        time = strfdelta(time_till_brexit,"{hours} hours, {minutes} and {seconds} to go")
        #time =  '%s Hours : %s Minutes : %s Seconds' % (time_till_brexit.Hour, time_till_brexit.minutes, time_till_brexit.seconds)
        return render(request, 'polls/index.html', {'time': time})

