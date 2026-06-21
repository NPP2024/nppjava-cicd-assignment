# Assignment: Build a CI/CD Pipeline

**Estimated time:** 4–6 hours · **Work:** individual · **Submit:** link to your
GitHub repo + a short reflection (see end)

## Goal

You are given a working, tested Java web service. Production teams never merge
code without an automated pipeline that builds it, tests it, checks its quality,
and ships it. Your task is to build that pipeline in **GitHub Actions** by
growing the starter file at `.github/workflows/ci.yml`.

## Learning objectives

By the end you should be able to:

1. Configure event triggers so CI runs on the right pushes and pull requests.
2. Use caching and job structure to keep pipelines fast and readable.
3. Surface test results and enforce a code-coverage quality gate.
4. Add static analysis (linting) as an independent signal.
5. Build a container image and publish it to a registry as a deploy step.
6. Reason about *ordering and conditions* — what should run, when, and only if
   what came before succeeded.

## Setup

1. Create a **new** GitHub repository (public or private) and push this code to it.
2. Confirm the starter pipeline runs: go to the **Actions** tab after your first
   push. The `build-and-test` job should pass.
3. Work on a feature branch and open pull requests — that's part of the point.

## Tasks

Each task corresponds to a `TODO` comment in `.github/workflows/ci.yml` (and
Task 4 also touches `pom.xml`). Complete them in order.

**Task 1 — Run on pull requests.** Add a `pull_request` trigger so the pipeline
runs on PRs into `main`, not just direct pushes. *Verify:* open a PR and watch
the check appear on it.

**Task 2 — Cache dependencies.** Enable Maven caching in the `setup-java` step.
*Verify:* the second run of a job should be noticeably faster and the log should
show a cache hit.

**Task 3 — Publish test results.** Upload `target/surefire-reports` as a build
artifact, and make sure it uploads **even when tests fail**. *Verify:* download
the artifact from a completed run.

**Task 4 — Coverage gate.** Uncomment the JaCoCo `coverage-check` execution in
`pom.xml` and ensure `mvn verify` enforces it. *Verify:* temporarily delete a
test, confirm the build fails on coverage, then restore the test.

**Task 5 — Lint job.** Add a separate `lint` job that runs
`mvn -B checkstyle:check`. *Verify:* introduce a style violation (e.g. an unused
import), watch `lint` fail while `build-and-test` still passes, then fix it.

**Task 6 — Deploy (CD).** Add a `docker` job that builds the image from the
`Dockerfile` and pushes it to **GitHub Container Registry (ghcr.io)**. It must:
- run only on pushes to `main` (not on PRs),
- run only **after** `build-and-test` and `lint` succeed,
- request `packages: write` permission.

*Verify:* after merging to `main`, find your published image under the repo's
**Packages**.

## Stretch goals (optional, for extra credit)

- **Matrix build:** run `build-and-test` across JDK 17 **and** 21.
- **Release on tag:** add a trigger so that pushing a tag like `v1.0.0` also
  tags the Docker image with that version.
- **Branch protection:** configure the repo so `main` can't be merged into
  unless CI passes, and describe what you did in your reflection.
- **PR coverage comment:** post the coverage summary as a comment on the PR.

## What to submit

1. A link to your repository with a **green** Actions run on `main`.
2. A link to the **published container image** under Packages.
3. A `REFLECTION.md` (½–1 page) answering:
   - Which task was hardest and why?
   - Why do test results and linting live in separate jobs?
   - Why does the deploy job have an `if:` condition? What would break without it?
   - One thing you'd add if this were a real production pipeline.

## Rules

- Don't modify the application code or tests to make a gate pass (except where a
  task explicitly tells you to break/restore a test to observe behaviour).
- All pipeline work goes in `.github/workflows/` and `pom.xml`.
- Commit history matters: small, meaningful commits beat one giant commit.
