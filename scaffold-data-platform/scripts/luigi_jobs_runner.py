#!/usr/bin/env python
import luigi
from scaffold_data_platform import ScaffoldDataPlatform
from datetime import datetime, timedelta
import sys


# if set, use --date parameter
if '--date' in sys.argv and len(sys.argv) > sys.argv.index('--date') + 1:
    date = sys.argv[sys.argv.index('--date') + 1]
# otherwise compute yesterday's date
else:
    yesterday = datetime.today() - timedelta(1)
    date = yesterday.strftime('%Y-%m-%d')

workers = 3
if '--workers' in sys.argv:
    workers = sys.argv[sys.argv.index('--workers') + 1]

args = ['--date', date]
args.extend(['--workers', str(workers)])

# prepare the argument list
if __name__ == '__main__':
    luigi.run(main_task_cls=ScaffoldDataPlatform, cmdline_args=args)
