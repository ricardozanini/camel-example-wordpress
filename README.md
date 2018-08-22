# Apache Camel Wordpress Example

This is a simple example scenario of how to use the [camel-wordpress](https://github.com/apache/camel/blob/master/components/camel-wordpress/src/main/docs/wordpress-component.adoc) component. 

In this example, we see how to use this component to publish an auto generated news post based on a [soccer statistics API](https://www.football-data.org/). The example consumes the statistics API, generates the text based on a [Natural Language Generation (NLG) library](https://github.com/simplenlg/simplenlg) and them publishes it to the Wordpress blog.

## How to use

If you have an OpenShift installation, just add the template to your project and create a new app based on it:

```shell
git clone git@github.com:ricardozanini/camel-example-wordpress.git

oc create -f openshift/camel-wordpress-sample-template.yaml
```

If you do not, just edit the `src/main/resources/application.properties` file with your environment vars and try it out with a simple HTTP GET to get a summary of a certain match:

```shell
curl http://localhost:8080/api/match/158186/summary
```

To send this summary to a Wordpress blog post just:


```shell
curl http://localhost:8080/api/match/158186/send
```


### Wordpress installation steps

If you use the OpenShift template, you have to follow these steps to get everything working:

1. Open Wordpress and finish the installation by adding the same user informed during the project creation
2. Login at Wordpress dashboard and activate the `Basic Auth Plugin`
3. Update the **Permalink** options to enable the `wp-json` path

Or you just install the [`Basic Auth Plugin`](https://github.com/WP-API/Basic-Auth) on your own Wordpress blog.
