//
//  WebBrowserViewController.swift
//  WLOY App
//
//  Created by William Robbins on 3/29/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import UIKit

class WebBrowserViewController: UIViewController {

    // CONSTANTS
    let WLOY_HOME = "http://www.wloy.org"
    
    // VARIABLES
    static var instance:WebBrowserViewController!
    
    // OUTLETS
    @IBOutlet weak var browser: UIWebView!
    
    // METHODS
    // staticLoadWebpage - load a webpage to the browser from any other menu
    static func staticLoadWebpage(url:String) {
        if(instance != nil) {
            instance.loadWebpage(url)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        WebBrowserViewController.instance = self
        loadHomePage()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // loadWebpage - load a webpage from a URL string
    func loadWebpage(url:String) {
        let request = NSURLRequest(URL:NSURL(string:url)!)
        browser.loadRequest(request)
    }
    
    // loadHomePage - load the WLOY website homepage
    func loadHomePage() {
        loadWebpage(WLOY_HOME)
    }
}
