from locust import task, FastHttpUser, stats

class HelloWorld(FastHttpUser):
    connection_timeout = 10.0
    network_timeout = 10.0

    @task
    def hello(self):
        response = self.client.get("/hello")