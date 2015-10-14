import sys
import os
from nose.tools import with_setup
from nose.plugins.attrib import attr
import logging

current_dir = os.path.dirname(os.path.realpath(__file__))
root_dir = os.path.dirname(current_dir)
lib_dir = os.path.join(root_dir, 'sdetpylib')
sys.path.append(lib_dir)
vr_dir = os.path.join(root_dir, 'vrlib')
sys.path.append(vr_dir)

from sdet_nose_assert import *
from sdet_nose_plugin import *
import sdet_random
import sdet_rest
import sdet_time
import sdet_nose_util

def setup_func(func_name):
	"set up test fixtures"
    
	logging.info('this is setup_func')

def teardown_func(func_name):
	"tear down test fixtures"

	logging.info('this is teardown_func')
  
def with_named_setup(setup=None, teardown=None):
	def wrap(f):
		return with_setup(
			lambda: setup(f.__name__) if (setup is not None) else None,
			lambda: teardown(f.__name__) if (teardown is not None) else None)(f)
	return wrap

################## variables defination ###############
htc_act_id = 'd336f704-a85b-4e3e-b291-d5791d777cd2'
test_appId = 'A01'
test_hmdId = 'edrfvtg2345'
test_tag = 'tag1'
test_versionId = '1001'

##########

