import luigi
from luigi.hdfs import HdfsTarget
from scaffold import configuration

config = configuration.get_config()


class ScaffoldDataPlatform(luigi.Task):
    date = luigi.DateParameter()

    def output(self):
        return HdfsTarget(config.get("job_args", "output") + "_SUCCESS")

    def requires(self):
        return { }

    def run(self):
        with self.output().open("w") as out_file:
            print >> out_file, "SUCCESS"

if __name__ == '__main__':
    luigi.run(main_task_cls=ScaffoldDataPlatform)
