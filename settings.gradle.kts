/*
 * Copyright (c) 2024. Andrea Giulianelli
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.13"
    id("com.gradle.develocity") version("3.18.1")
}

rootProject.name = "kotlin-template-project"

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")
        publishing.onlyIf { it.buildResult.failures.isNotEmpty() } // Publish the build scan when the build fails
    }
}

gitHooks {
    preCommit {
        tasks("detekt")
        tasks("ktlintCheck")
    }

    commitMsg {
        conventionalCommits()
    }

    hook("post-commit") {
        from {
            "git verify-commit HEAD &> /dev/null; " +
                "if (( $? == 1 )); then echo -e '\\033[0;31mWARNING(COMMIT UNVERIFIED): commit NOT signed\\033[0m';" +
                "else echo -e '\\033[0;32mOK COMMIT SIGNED\\033[0m'; fi"
        }
    }

    createHooks(true)
}
