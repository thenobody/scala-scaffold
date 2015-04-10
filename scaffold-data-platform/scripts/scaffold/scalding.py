import luigi
import configuration
from luigi.hadoop_jar import HadoopJarJobRunner
import logging


logger = logging.getLogger('luigi-interface')
config = configuration.get_config()


class ScaldingHadoopJarJobTask(luigi.hadoop_jar.HadoopJarJobTask):
    def jar(self):
        return None

    def job_class(self):
        return None

    def job_runner(self):
        return HadoopJarJobRunner()

    def job_args(self):
        return []

    def atomic_output(self):
        """If True, then rewrite output arguments to be temp locations and
        atomically move them into place after the job finishes"""
        return True

    def jobconfs(self):
        return [k + "=%s" % v for k, v in self.get_hadoop_conf().items()]

    def get_hadoop_conf(self):
        params = {}

        if config.has_section('hadoop_params'):
            params = {k: v for (k, v) in config.items('hadoop_params')}

        params.update({
            'mapreduce.job.name':       self.task_id,
            'mapreduce.job.priority':   self.get_job_priority()
        })
        return params

    def get_job_priority(self):
        try:
            number_of_days = self.number_of_days
            if number_of_days == 0:
                priority = "HIGH"
            else:
                priority = "NORMAL"
        except AttributeError:
            priority = "NORMAL"
        return priority

    def args(self):
        args = []
        args.append('--hdfs')
        args.extend(self.job_args())
        args.extend(['--output', self.output()])
        return args