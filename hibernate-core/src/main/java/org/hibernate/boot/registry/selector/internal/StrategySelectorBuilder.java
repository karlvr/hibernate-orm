package org.hibernate.boot.registry.selector.internal;

import org.hibernate.boot.registry.selector.StrategyRegistrationProvider;
import org.hibernate.dialect.CUBRIDDialect;
import org.hibernate.dialect.Cache71Dialect;
import org.hibernate.dialect.DB2390Dialect;
import org.hibernate.dialect.DB2400Dialect;
import org.hibernate.dialect.DB2Dialect;
import org.hibernate.dialect.DerbyTenFiveDialect;
import org.hibernate.dialect.DerbyTenSevenDialect;
import org.hibernate.dialect.DerbyTenSixDialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.FirebirdDialect;
import org.hibernate.dialect.FrontBaseDialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.dialect.InformixDialect;
import org.hibernate.dialect.Ingres10Dialect;
import org.hibernate.dialect.Ingres9Dialect;
import org.hibernate.dialect.IngresDialect;
import org.hibernate.dialect.InterbaseDialect;
import org.hibernate.dialect.JDataStoreDialect;
import org.hibernate.dialect.MckoiDialect;
import org.hibernate.dialect.MimerSQLDialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.dialect.Oracle9iDialect;
import org.hibernate.dialect.PointbaseDialect;
import org.hibernate.dialect.PostgreSQL81Dialect;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.dialect.ProgressDialect;
import org.hibernate.dialect.SAPDBDialect;
import org.hibernate.dialect.SQLServer2005Dialect;
import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.Sybase11Dialect;
import org.hibernate.dialect.SybaseASE157Dialect;
import org.hibernate.dialect.SybaseASE15Dialect;
import org.hibernate.dialect.SybaseAnywhereDialect;
import org.hibernate.dialect.TeradataDialect;
import org.hibernate.dialect.TimesTenDialect;
import org.hibernate.engine.transaction.internal.jdbc.JdbcTransactionFactory;
import org.hibernate.engine.transaction.internal.jta.CMTTransactionFactory;
import org.hibernate.engine.transaction.internal.jta.JtaTransactionFactory;
import org.hibernate.engine.transaction.jta.platform.internal.BitronixJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.BorlandEnterpriseServerJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.JBossAppServerJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.JBossStandAloneJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.JOTMJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.JOnASJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.JRun4JtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.OC4JJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.OrionJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.ResinJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.SunOneJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.WebSphereExtendedJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.WebSphereJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.internal.WeblogicJtaPlatform;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;
import org.hibernate.engine.transaction.spi.TransactionFactory;
import org.hibernate.hql.spi.MultiTableBulkIdStrategy;
import org.hibernate.hql.spi.PersistentTableBulkIdStrategy;
import org.hibernate.hql.spi.TemporaryTableBulkIdStrategy;
import org.hibernate.service.boot.selector.internal.StrategySelectorImpl;


public class StrategySelectorBuilder extends org.hibernate.service.boot.selector.internal.AbstractStrategySelectorBuilder {

	public StrategySelectorBuilder() {
		super(StrategyRegistrationProvider.class);
	}

	@Override
	protected void addBaseline(StrategySelectorImpl strategySelector) {
		addDialects( strategySelector );
		addJtaPlatforms( strategySelector );
		addTransactionFactories( strategySelector );
		addMultiTableBulkIdStrategies( strategySelector );
	}

	private void addDialects(StrategySelectorImpl strategySelector) {
		addDialect( strategySelector, Cache71Dialect.class );
		addDialect( strategySelector, CUBRIDDialect.class );
		addDialect( strategySelector, DB2Dialect.class );
		addDialect( strategySelector, DB2390Dialect.class );
		addDialect( strategySelector, DB2400Dialect.class );
		addDialect( strategySelector, DerbyTenFiveDialect.class );
		addDialect( strategySelector, DerbyTenSixDialect.class );
		addDialect( strategySelector, DerbyTenSevenDialect.class );
		addDialect( strategySelector, FirebirdDialect.class );
		addDialect( strategySelector, FrontBaseDialect.class );
		addDialect( strategySelector, H2Dialect.class );
		addDialect( strategySelector, HSQLDialect.class );
		addDialect( strategySelector, InformixDialect.class );
		addDialect( strategySelector, IngresDialect.class );
		addDialect( strategySelector, Ingres9Dialect.class );
		addDialect( strategySelector, Ingres10Dialect.class );
		addDialect( strategySelector, InterbaseDialect.class );
		addDialect( strategySelector, JDataStoreDialect.class );
		addDialect( strategySelector, MckoiDialect.class );
		addDialect( strategySelector, MimerSQLDialect.class );
		addDialect( strategySelector, MySQL5Dialect.class );
		addDialect( strategySelector, MySQL5InnoDBDialect.class );
		addDialect( strategySelector, MySQL5Dialect.class );
		addDialect( strategySelector, MySQL5InnoDBDialect.class );
		addDialect( strategySelector, Oracle8iDialect.class );
		addDialect( strategySelector, Oracle9iDialect.class );
		addDialect( strategySelector, Oracle10gDialect.class );
		addDialect( strategySelector, PointbaseDialect.class );
		addDialect( strategySelector, PostgresPlusDialect.class );
		addDialect( strategySelector, PostgreSQL81Dialect.class );
		addDialect( strategySelector, PostgreSQL82Dialect.class );
		addDialect( strategySelector, PostgreSQL9Dialect.class );
		addDialect( strategySelector, ProgressDialect.class );
		addDialect( strategySelector, SAPDBDialect.class );
		addDialect( strategySelector, SQLServerDialect.class );
		addDialect( strategySelector, SQLServer2005Dialect.class );
		addDialect( strategySelector, SQLServer2008Dialect.class );
		addDialect( strategySelector, Sybase11Dialect.class );
		addDialect( strategySelector, SybaseAnywhereDialect.class );
		addDialect( strategySelector, SybaseASE15Dialect.class );
		addDialect( strategySelector, SybaseASE157Dialect.class );
		addDialect( strategySelector, TeradataDialect.class );
		addDialect( strategySelector, TimesTenDialect.class );
	}

	private void addDialect(StrategySelectorImpl strategySelector, Class<? extends Dialect> dialectClass) {
		String simpleName = dialectClass.getSimpleName();
		if ( simpleName.endsWith( "Dialect" ) ) {
			simpleName = simpleName.substring( 0, simpleName.length() - "Dialect".length() );
		}
		strategySelector.registerStrategyImplementor( Dialect.class, simpleName, dialectClass );
	}

	private void addJtaPlatforms(StrategySelectorImpl strategySelector) {
		addJtaPlatforms(
				strategySelector,
				BorlandEnterpriseServerJtaPlatform.class,
				"Borland",
				"org.hibernate.service.jta.platform.internal.BorlandEnterpriseServerJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				BitronixJtaPlatform.class,
				"Bitronix",
				"org.hibernate.service.jta.platform.internal.BitronixJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				JBossAppServerJtaPlatform.class,
				"JBossAS",
				"org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				JBossStandAloneJtaPlatform.class,
				"JBossTS",
				"org.hibernate.service.jta.platform.internal.JBossStandAloneJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				JOnASJtaPlatform.class,
				"JOnAS",
				"org.hibernate.service.jta.platform.internal.JOnASJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				JOTMJtaPlatform.class,
				"JOTM",
				"org.hibernate.service.jta.platform.internal.JOTMJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				JRun4JtaPlatform.class,
				"JRun4",
				"org.hibernate.service.jta.platform.internal.JRun4JtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				OC4JJtaPlatform.class,
				"OC4J",
				"org.hibernate.service.jta.platform.internal.OC4JJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				OrionJtaPlatform.class,
				"Orion",
				"org.hibernate.service.jta.platform.internal.OrionJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				ResinJtaPlatform.class,
				"Resin",
				"org.hibernate.service.jta.platform.internal.ResinJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				SunOneJtaPlatform.class,
				"SunOne",
				"org.hibernate.service.jta.platform.internal.SunOneJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				WeblogicJtaPlatform.class,
				"Weblogic",
				"org.hibernate.service.jta.platform.internal.WeblogicJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				WebSphereJtaPlatform.class,
				"WebSphere",
				"org.hibernate.service.jta.platform.internal.WebSphereJtaPlatform"
		);

		addJtaPlatforms(
				strategySelector,
				WebSphereExtendedJtaPlatform.class,
				"WebSphereExtended",
				"org.hibernate.service.jta.platform.internal.WebSphereExtendedJtaPlatform"
		);
	}

	private void addJtaPlatforms(StrategySelectorImpl strategySelector, Class<? extends JtaPlatform> impl, String... names) {
		for ( String name : names ) {
			strategySelector.registerStrategyImplementor( JtaPlatform.class, name, impl );
		}
	}

	private void addTransactionFactories(StrategySelectorImpl strategySelector) {
		strategySelector.registerStrategyImplementor( TransactionFactory.class, JdbcTransactionFactory.SHORT_NAME, JdbcTransactionFactory.class );
		strategySelector.registerStrategyImplementor( TransactionFactory.class, "org.hibernate.transaction.JDBCTransactionFactory", JdbcTransactionFactory.class );

		strategySelector.registerStrategyImplementor( TransactionFactory.class, JtaTransactionFactory.SHORT_NAME, JtaTransactionFactory.class );
		strategySelector.registerStrategyImplementor( TransactionFactory.class, "org.hibernate.transaction.JTATransactionFactory", JtaTransactionFactory.class );

		strategySelector.registerStrategyImplementor( TransactionFactory.class, CMTTransactionFactory.SHORT_NAME, CMTTransactionFactory.class );
		strategySelector.registerStrategyImplementor( TransactionFactory.class, "org.hibernate.transaction.CMTTransactionFactory", CMTTransactionFactory.class );
	}

	private void addMultiTableBulkIdStrategies(StrategySelectorImpl strategySelector) {
		strategySelector.registerStrategyImplementor(
				MultiTableBulkIdStrategy.class,
				PersistentTableBulkIdStrategy.SHORT_NAME,
				PersistentTableBulkIdStrategy.class
		);
		strategySelector.registerStrategyImplementor(
				MultiTableBulkIdStrategy.class,
				TemporaryTableBulkIdStrategy.SHORT_NAME,
				TemporaryTableBulkIdStrategy.class
		);
	}
	
}
