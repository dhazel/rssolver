
Rssolver
========

Turn any website into an RSS feed.

## Contents

* [What is Rssolver?](#what-is-rssifier?)
* [Why RSS?](#why-rss?)
* [Installation](#installation)
* [Configuration](#configuration)
* [Usage](#usage)
* [Technical Overview](#technical-overview)
* [Motivations](#motivations)


## What is Rssolver?

Rssolver is a simple web scraper that, given a few configuration parameters,
will pull data from any website, reformat that data into an RSS2 feed, and
then serve that data to the feed reader of your choice.

## Why RSS?

RSS readers offer added features on top of traditional web content. With an
RSS reader, a user can filter and favorite content, store history of content,
and more.

For example, given an online marketplace, a feed reader can help the user save,
categorize, and track product listings. Thus, a user can "feel out" the market,
spot flippers, etc.

## Installation

Java is required. You must have a JVM installed.

This is currently technical and aimed at developers. To get Rssolver, you must
download or clone this repository.

## Usage

Open a command prompt, navigate to the directory where you have stored
the root of this repository, then type the following command.

    ./mill app.run

This will launch a server on your local computer at the port specified in the
configuration. The server will then serve whatever RSS feeds that you have
configured sources for.

Finally, connect an RSS reader of your choice to the feeds you have configured.

For example, if the configuration specifies port 8081, and you configured an
RSS source named "mysource", set your RSS reader to read from the following url:

    http://localhost:8081/rss/mysource

## Configuration

TBD

## Technical Overview

* Language: Scala
* Build tool: Mill
* Testing framework: ScalaTest
* Web server: Undertow
* Web framework: Cask
* Scraping tool: XSoup

## Motivations

1. A website whose RSS feeds I used to rely on stopped supporting RSS. Rssolver
enables me to continue using RSS with this website or any other website I choose.
2. My Scala skills had grown rusty. I wanted a project to re-sharpen those
skills and explore the current Scala ecosystem.
3. Enrichen the Open Source community.


