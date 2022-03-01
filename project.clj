(defproject bugger "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0",
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :main bugger.core
  :repl-options {:init-ns bugger.core}
  :mirrors {#"clojars" {:name "clojars-ustc",
                        :url "https://mirrors.ustc.edu.cn/clojars/",
                        :repo-manager true},
            #"central" {:name "central-aliyun",
                        :url "https://maven.aliyun.com/repository/public",
                        :repo-manager true}}
  :repositories
    [["sonatype-snapshot"
      "https://s01.oss.sonatype.org/content/repositories/snapshots"]]
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [random-string "0.1.0"]
                 [org.clojure/tools.logging "1.2.4"]
                 [io.hstream/hstreamdb-java "0.7.1-SNAPSHOT"]])
