import brave.sampler.Sampler
import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariConfig
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.hikari.HikariModule
import ratpack.hub.HubDAOService
import ratpack.hub.HubsEndpoint
import ratpack.zipkin.ServerTracingModule
import zipkin.ZipEndpoint
import zipkin2.reporter.AsyncReporter
import zipkin2.reporter.okhttp3.OkHttpSender

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
	bindings {
		module MarkupTemplateModule

		//Adds the Hikari Module which will be our db connection pool
		module HikariModule, { HikariConfig c ->
			c.addDataSourceProperty("URL", "jdbc:h2:mem:dev;INIT=CREATE SCHEMA IF NOT EXISTS DEV")
			c.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource")
		}

		moduleConfig(ServerTracingModule,  serverConfig.get('/zipkin', ServerTracingModule.Config)
				.serviceName("ratpack")
				.sampler(Sampler.ALWAYS_SAMPLE)
				.spanReporterV2(AsyncReporter.create(OkHttpSender.create("http://localhost:9411/api/v2/spans")))

		)

		bind(HubDAOService)
		bind(HubsEndpoint)
		bind(ZipEndpoint)

	}

	handlers {
		get {
			render groovyMarkupTemplate("index.gtpl", title: "My ratpack App")
		}

		prefix("zip"){
			all chain(registry.get(ZipEndpoint))
		}

		prefix("hubs") {
			all chain(registry.get(HubsEndpoint))
		}

		files { dir "public" }
	}
}
