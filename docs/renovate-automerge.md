# Renovate automerge

## Current configuration

`civil-wa-task-configuration` uses `.github/renovate.json`, extending the shared HMCTS preset and `local>hmcts/.github//renovate/automerge-all`.

Repo-level settings:

- `timezone`: `Europe/London`.
- `schedule` and `automergeSchedule`: after 7am and before 9am, and after 5pm and before 8pm, every weekday.
- `platformAutomerge`: `false`, so Renovate enforces the configured merge windows instead of queuing GitHub auto-merge.
- `rebaseWhen`: `behind-base-branch`, so Renovate refreshes branches that fall behind `master`.
- `automergeStrategy`: `squash`.
- `prConcurrentLimit`: `5`.

Groups, in the order they appear in `packageRules`:

| Group | Matches | Automerge |
|---|---|---|
| All non-major updates | every minor and patch update not taken by a group below | yes |
| Helm chart updates | `helmv3` manager, `docker` datasource | yes |
| JUnit | `org.junit.*` | yes |
| Spring | `org.springframework.*`, `io.spring.*` | yes |
| Camunda | `org.camunda.*` | yes |
| Log4j | `org.apache.logging.log4j` | yes |
| Gradle build tools | Gradle, ben-manes versions, OWASP dependency-check, SonarQube, Freefair | yes |
| Terraform version | `infrastructure/.terraform-version` | no |
| Jenkins library | `hmcts/cnp-jenkins-library` | yes (from the preset) |

### Group order

When several package rules match, Renovate applies them in order and later values win. "All non-major updates" must stay first, so each named group below it takes its own packages out of the catch-all and gets its own PR for every update type. Renovate still raises majors on a separate branch (for example `renovate/major-spring`).

Until this was fixed, "All non-major updates" came after the named groups and replaced their `groupName`. Minor and patch updates for Helm charts, JUnit, Spring, Camunda, Log4j and the Gradle build tools all landed in the "All non-major updates" PR, and the named groups only ever received majors. For example, Helm image tag bumps such as `civil-service` v0.0.133 and v0.0.134 arrived on `renovate/all-non-major`.

### Major updates automerge

The `automerge-all` preset sets `automerge: true` for every update, majors included, and no rule here turns it off except for the Terraform version. **Major Spring, Camunda, JUnit, Log4j, Gradle tool and Helm updates therefore automerge** once their checks pass. `civil-service` keeps Camunda updates and Spring majors for manual review; consider doing the same here.

## Code-owner review

`CODEOWNERS` assigns `*` to `@hmcts/civil`, then lists the files Renovate updates without an owner, so Renovate PRs that only touch them don't need code-owner review: `.nvmrc`, `Dockerfile`, `Jenkinsfile_CNP`, `build.gradle`, the Gradle wrapper files, `charts/**/Chart.yaml`, `infrastructure/.terraform-version`, and `.github/workflows/*.yaml` and `*.yml`. `charts/` and `infrastructure/` are otherwise owned by `@hmcts/civil-admins`.

## Merge window and Jenkins availability

PTL Jenkins runs on `cft-ptl-00-aks`, which [`hmcts/auto-shutdown`](https://github.com/hmcts/auto-shutdown) normally stops from 20:00 to 06:30 daily. There is no scheduled weekend startup.

If Renovate updates or rebases a PR while Jenkins is unavailable, the required check can't complete and automerge stays blocked. Branch creation and automerge therefore share the 07:00–09:00 and 17:00–20:00 weekday windows, when Jenkins is online and developer activity is lower. A PR rebased late in the evening window may not finish its build before the 20:00 shutdown; it then merges in a later window.

`platformAutomerge` must stay `false`; otherwise Renovate enables GitHub auto-merge and GitHub can merge whenever checks pass, regardless of `automergeSchedule`.

## Holds

There are no version holds or disabled packages in `.github/renovate.json`. When a Renovate PR fails the build on every rebase, find the cause and add a hold with the reason in its `description`, rather than leaving the PR open: with `rebaseWhen: behind-base-branch`, a PR that can never pass is rebuilt on every merge to `master`.

## Stalled-queue alert

`civil-service`'s `.github/workflows/renovate-stalled-alert.yml` also checks this repository's open Renovate PRs each weekday morning and reports any older than three days.

## Release freeze

Use a Renovate package rule for a planned release freeze, so the freeze is reviewable and recorded in the repository. Add it as the final item under `packageRules` in `.github/renovate.json`:

```json
{
  "description": "Release freeze: disable Renovate automerge",
  "matchPackageNames": ["*"],
  "automerge": false
}
```

It must stay last: later rules win, and every group above sets `automerge: true`. The rule stops automerge but leaves Renovate opening and rebasing PRs.

### Start a freeze

1. Add the freeze rule in a pull request, with the release or change reference and the intended end date in the description.
2. Merge it before the freeze starts, then run Renovate manually or wait for its next scheduled run.
3. Confirm in the Mend job log that the rule resolves to `automerge: false`, and record that in the release ticket.

### End a freeze

1. Remove the freeze rule through a pull request once the release is approved.
2. Merge it, then run Renovate manually or wait for its next scheduled run.
3. Check that a clean Renovate PR automerges only after all required checks pass.

## Pausing Renovate

Pausing is broader than a release freeze: it also stops new PRs, rebases and PR maintenance.

| | Release freeze (`automerge: false` rule) | Pause |
|---|---|---|
| New PRs and rebases | continue | stop |
| Automerge | stops | stops |
| Renovate's PR queue | stays current; merges are manual | builds up until turned back on |

There are two ways to pause:

- **In the repository:** set `"enabled": false` at the top level of `.github/renovate.json` in a pull request, with the reason and expected end. It takes effect once merged and Renovate next runs. Revert the pull request to turn Renovate back on.
- **In the Mend Developer Portal:** an organisation admin can pause the repository at `https://developer.mend.io/github/hmcts/civil-wa-task-configuration/-/settings`. This takes effect without a merge, so it suits a freeze that has to start straight away. Turn it back on in the portal.

Changing branch protection is not recommended as a routine toggle: it affects all contributors and makes intentional freezes look like authorisation failures.
