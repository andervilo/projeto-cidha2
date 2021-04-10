package br.com.cidha.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, br.com.cidha.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, br.com.cidha.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, br.com.cidha.domain.User.class.getName());
            createCache(cm, br.com.cidha.domain.Authority.class.getName());
            createCache(cm, br.com.cidha.domain.User.class.getName() + ".authorities");
            createCache(cm, br.com.cidha.domain.Comarca.class.getName());
            createCache(cm, br.com.cidha.domain.Comarca.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.Quilombo.class.getName());
            createCache(cm, br.com.cidha.domain.Quilombo.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.Processo.class.getName());
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".concessaoLiminars");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".concessaoLiminarCassadas");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".embargoRespRes");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".embargoDeclaracaoAgravos");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".embargoDeclaracaos");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".embargoRecursoEspecials");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".comarcas");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".quilombos");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".municipios");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".territorios");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".atividadeExploracaoIlegals");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".unidadeConservacaos");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".envolvidosConflitoLitigios");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".terraIndigenas");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".processoConflitos");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".parteInteresssadas");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".relators");
            createCache(cm, br.com.cidha.domain.Processo.class.getName() + ".problemaJuridicos");
            createCache(cm, br.com.cidha.domain.ConcessaoLiminar.class.getName());
            createCache(cm, br.com.cidha.domain.TipoEmpreendimento.class.getName());
            createCache(cm, br.com.cidha.domain.TipoDecisao.class.getName());
            createCache(cm, br.com.cidha.domain.EmbargoRespRe.class.getName());
            createCache(cm, br.com.cidha.domain.ConcessaoLiminarCassada.class.getName());
            createCache(cm, br.com.cidha.domain.EmbargoDeclaracao.class.getName());
            createCache(cm, br.com.cidha.domain.EmbargoDeclaracaoAgravo.class.getName());
            createCache(cm, br.com.cidha.domain.Recurso.class.getName());
            createCache(cm, br.com.cidha.domain.TipoRecurso.class.getName());
            createCache(cm, br.com.cidha.domain.OpcaoRecurso.class.getName());
            createCache(cm, br.com.cidha.domain.EnvolvidosConflitoLitigio.class.getName());
            createCache(cm, br.com.cidha.domain.EnvolvidosConflitoLitigio.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.TipoData.class.getName());
            createCache(cm, br.com.cidha.domain.Data.class.getName());
            createCache(cm, br.com.cidha.domain.FundamentacaoDoutrinaria.class.getName());
            createCache(cm, br.com.cidha.domain.FundamentacaoDoutrinaria.class.getName() + ".problemaJuridicos");
            createCache(cm, br.com.cidha.domain.Jurisprudencia.class.getName());
            createCache(cm, br.com.cidha.domain.Jurisprudencia.class.getName() + ".problemaJuridicos");
            createCache(cm, br.com.cidha.domain.FundamentacaoLegal.class.getName());
            createCache(cm, br.com.cidha.domain.FundamentacaoLegal.class.getName() + ".problemaJuridicos");
            createCache(cm, br.com.cidha.domain.InstrumentoInternacional.class.getName());
            createCache(cm, br.com.cidha.domain.InstrumentoInternacional.class.getName() + ".problemaJuridicos");
            createCache(cm, br.com.cidha.domain.ProblemaJuridico.class.getName());
            createCache(cm, br.com.cidha.domain.ProblemaJuridico.class.getName() + ".fundamentacaoDoutrinarias");
            createCache(cm, br.com.cidha.domain.ProblemaJuridico.class.getName() + ".jurisprudencias");
            createCache(cm, br.com.cidha.domain.ProblemaJuridico.class.getName() + ".fundamentacaoLegals");
            createCache(cm, br.com.cidha.domain.ProblemaJuridico.class.getName() + ".instrumentoInternacionals");
            createCache(cm, br.com.cidha.domain.ProblemaJuridico.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.Municipio.class.getName());
            createCache(cm, br.com.cidha.domain.Municipio.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.Territorio.class.getName());
            createCache(cm, br.com.cidha.domain.Territorio.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.EmbargoRecursoEspecial.class.getName());
            createCache(cm, br.com.cidha.domain.AtividadeExploracaoIlegal.class.getName());
            createCache(cm, br.com.cidha.domain.AtividadeExploracaoIlegal.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.UnidadeConservacao.class.getName());
            createCache(cm, br.com.cidha.domain.UnidadeConservacao.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.TerraIndigena.class.getName());
            createCache(cm, br.com.cidha.domain.TerraIndigena.class.getName() + ".etnias");
            createCache(cm, br.com.cidha.domain.TerraIndigena.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.EtniaIndigena.class.getName());
            createCache(cm, br.com.cidha.domain.EtniaIndigena.class.getName() + ".terraIndigenas");
            createCache(cm, br.com.cidha.domain.Direito.class.getName());
            createCache(cm, br.com.cidha.domain.Direito.class.getName() + ".processoConflitos");
            createCache(cm, br.com.cidha.domain.Conflito.class.getName());
            createCache(cm, br.com.cidha.domain.ProcessoConflito.class.getName());
            createCache(cm, br.com.cidha.domain.ProcessoConflito.class.getName() + ".conflitos");
            createCache(cm, br.com.cidha.domain.ProcessoConflito.class.getName() + ".direitos");
            createCache(cm, br.com.cidha.domain.ProcessoConflito.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.TipoRepresentante.class.getName());
            createCache(cm, br.com.cidha.domain.RepresentanteLegal.class.getName());
            createCache(cm, br.com.cidha.domain.RepresentanteLegal.class.getName() + ".processoConflitos");
            createCache(cm, br.com.cidha.domain.ParteInteresssada.class.getName());
            createCache(cm, br.com.cidha.domain.ParteInteresssada.class.getName() + ".representanteLegals");
            createCache(cm, br.com.cidha.domain.ParteInteresssada.class.getName() + ".processos");
            createCache(cm, br.com.cidha.domain.Relator.class.getName());
            createCache(cm, br.com.cidha.domain.Relator.class.getName() + ".processos");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
