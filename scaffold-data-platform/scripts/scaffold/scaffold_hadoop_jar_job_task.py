from scalding import ScaldingHadoopJarJobTask
import datetime
import configuration
import luigi
import re

config = configuration.get_config()


class ScaffoldHadoopJarJobTask(ScaldingHadoopJarJobTask):
    date_format = '%Y-%m-%d'
    output_folder_prefix = 'd='
    date_regex = '(2[0-9]{3}-[01][0-9]-[0-3][0-9])'

    def jar(self):
        return config.get("hadoop_jars", "main")

    def build_date_folder_name(self, date):
        return date.strftime(self.output_folder_prefix + self.date_format)

    def build_output_path(self, path, date, number_of_days=None):
        if path[-1:] != '/':
            path += '/'
        folder = self.build_date_folder_name(date)
        if number_of_days is not None and isinstance(number_of_days, int):
            folder += '/nd_' + str(number_of_days)
        return path + folder + '/'

    def format_date(self, date):
        return date.strftime(self.date_format)

    def get_from_date(self, to_date, number_of_days):
        return to_date - datetime.timedelta(days=number_of_days)

    def get_most_recent_date(self, path, date):
        hdfs_client = luigi.hdfs.client
        folders = hdfs_client.listdir(path)

        def extract_date(folder_name):
            groups = re.search(self.date_regex, folder_name)
            if groups:
                return groups.group(0)

        dates = [extract_date(folder) for folder in folders]
        dates = [d for d in dates if d and d <= date.strftime(self.date_format)]
        dates.sort()

        return dates[-1] if len(dates) > 0 else None