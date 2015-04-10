import luigi
from luigi.hdfs import HdfsTarget


class HdfsFileExternalTask(luigi.ExternalTask):
    path = luigi.Parameter()
    
    def output(self):
        return HdfsTarget(self.path)
