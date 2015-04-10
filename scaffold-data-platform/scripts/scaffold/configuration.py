import luigi


def get_config():
    config = luigi.configuration.get_config()
    config.add_config_path('/etc/scaffold/luigi.cfg')
    return config
