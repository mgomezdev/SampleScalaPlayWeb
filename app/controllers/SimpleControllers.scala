/**
 * Copyright (C) 2012 Gist Labs, LLC. (http://gistlabs.com)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package controllers

import play.api.mvc._
import java.lang.RuntimeException
import play.api.mvc.Cookie


object Application extends Controller {
  def index = Action {
    Ok(views.html.index())
  }
}


object ResponseCodesController extends Controller {
  def redir = Action {
    Redirect(routes.ResponseCodesController.redirTarget())
  }

  def redirTarget = Action {
    Ok(views.html.redirectTarget())
  }

  def infiniteRedir = Action {
    Redirect(routes.ResponseCodesController.infiniteRedir)
  }

  def strangeResponseCode = Action {
    Status(466)("466 Response code")
  }

  def internalServerError = Action {
    InternalServerError("Oops")
  }
}


object SimpleResultsController extends Controller {
  def xmlResult = Action {
    Ok(<message>Hello form GistLabs!</message>)
  }

  def echoTestTagHelp = Action {
    Ok(views.html.echoTestTagHelp())
  }

  def echoTestTagFromXml = Action {
    request =>
      request.body.asXml.map {
        xml =>
          (xml \\ "test" headOption).map(_.text).map {
            test =>
              Ok(views.xml.testTag(test))
          }.getOrElse {
            BadRequest("Missing parameter [test]")
          }
      }.getOrElse {
        BadRequest("Expecting Xml data")
      }
  }

  def echoCookies = Action {
    implicit request =>
      val cookies = request.headers.get(COOKIE).getOrElse("")
      Ok(views.html.echocookies(cookies)).withHeaders(SET_COOKIE -> cookies)
  }

  def customCookie = Action {
    Ok(views.html.customCookieTarget()).withCookies(Cookie("sender", "gistlabs"))
  }

  def imgResult = Action {
    Ok(views.html.imgexample())
  }
}