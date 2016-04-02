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
    
    // OUTLETS
    @IBOutlet weak var browser: UIWebView!
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        loadHomePage()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // loadHomePage - go to the WLOY home page
    func loadHomePage() {
        let homeRequest = NSURLRequest(URL:NSURL(string:WLOY_HOME)!)
        browser.loadRequest(homeRequest)
    }

}
