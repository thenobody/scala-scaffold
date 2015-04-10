import luigi


class LocalFileExternalTask(luigi.ExternalTask):
    path = luigi.Parameter()
    
    def output(self):
        return luigi.LocalTarget(self.path)
