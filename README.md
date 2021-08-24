[![TravisCI (.com) build status](https://img.shields.io/travis/com/jupyterhub/jupyterhub/master?logo=travis)](https://travis-ci.com/github/Mindisgone/trelloGithubWebhooksAPI)
[![GitHub](https://img.shields.io/badge/issue_tracking-trello-blue?logo=github)](https://trello.com/b/lAhgjtGK)
![Website](https://img.shields.io/website?down_color=red&down_message=offline&label=api&up_color=green&up_message=online&url=http%3A%2F%2Fworkflow-webhooks.com%2Fapi%2Fstatus)
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/Mindisgone/trelloGithubWebhooksAPI)
![GitHub issues](https://img.shields.io/github/issues/Mindisgone/trelloGithubWebhooksAPI)

# Trello/Github Webhooks API

## Intro

The Trello/Github Webhooks API is a restful API designed to receive
Github events from Github webhooks and comment on a corresponding Trello
card so Trello Butler can better automate your workflow.

## How it works

After this API receives a Github event from the webhook it looks at the
headers, and the payload to determine what type of event has been
received. The API then constructs a comment that gets posted to the
corresponding Trello card. The API uses the git branch name as the
mechanism for finding the corresponding Trello card, **therefor the
Trello card and its associated branch must be named exactly the same**.
Unfortunately the Github webhook events for comments on PR's (*not
reviews, those are different events*) do not hold a value for the branch
name like the other events do, instead **if you want PR comments to be
posted to your Trello cards you must also name the PR exactly the same
as the Trello card you want the comments posted to**.

The API will comment on the following Github events:

* PR's being opened
* PR's being closed
* Reviews on PR's
* Comments on PR's (see note above regarding comments)
* PR's being merged

Other events will be supported in future releases.

### Install

This API is a hosted solution, however a npm package and build
instructions will be posted here in a future release to support local
installations.

## Usage

> **This API does not store or log personal data (ie. your Trello key
> and token) in any way:heavy_exclamation_mark:**
>
> **When using this API keep in mind there are limits on the amount of
> Butler tasks you are allowed per month depending on your Trello
> account, see
> [Trello docs](https://help.trello.com/article/1181-butler-features-and-quotas)
> for more details**
> ![Butler Quotas](/.docs/butler_quotas.png)

In order to use the API you must:

1. Enable the Trello API on your account by following these
   [Instructions](https://developer.atlassian.com/cloud/trello/guides/rest-api/api-introduction/)
2. Create a new webhook for your Github repository using the following
   settings:
   * ![Webhooks Config](/.docs/webhooks_config.png)
   * Where ```TRELLO_KEY``` is your Trello API key and
     ```TRELLO_TOKEN``` is your Trello API app token
3. Be sure to **name your git branches exactly the same as the Trello
   cards you want to *link* together**
   * If you want to post PR comments to your Trello cards **your PR's
     must also be named exactly the same as the Trello cards you want to
     *link* them to**
4. Configure your Trello Butler automation rules to trigger on comments
   posted to cards from the API

### Example Workflow

For this example our Trello board will have the following lists:

```
1. Backlog
2. To Do
3. In Progress
4. Code Review
5. Testing
6. Business Review
7. Done
```

Our cards will utilize PR's ```opened```, ```closed```, and
```submitted``` events (which are reviews on PR's, for this example we
will not be using PR comments).

This example will achieve the following automation:

1. When a PR is made, Trello card will be moved to ```Code Review```
   list
2. When an approval on the PR is submitted with the text "```LGRTM```",
   move the card to ```Testing``` list
3. When PR is successfully merged, move the card to ```Business
   Review``` list

Your Trello automation rules will look similar to the following:

![Butler Rules Example](/.docs/butler_rules_example.png)

## Contributing

Contributions are very welcome. See the
[contribution guide](./CONTRIBUTING.md) to get started. See the
[Trello Board](https://trello.com/b/lAhgjtGK) for issues new features.
