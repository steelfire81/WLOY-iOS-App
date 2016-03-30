//
//  FirstViewController.swift
//  WLOY App
//
//  Created by William Robbins on 3/15/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import UIKit

class NowPlayingViewController: UIViewController {

    // CONSTANTS
    let DEFAULT_ARTIST = "Artist Name"
    let DEFAULT_SHOW = "Show Name"
    let DEFAULT_SONG = "Song Name"
    
    // OUTLETS
    @IBOutlet weak var volume: UISlider!
    @IBOutlet weak var nowPlayingLabel: UILabel!
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        setNowPlayingLabel(DEFAULT_ARTIST, song:DEFAULT_SONG)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // setCurrentShowLabel - update text on the current show field
    func setCurrentShowLabel(show: String) {
        
    }
    
    // setNowPlayingLabel - given an artist and song, update the now playing field
    func setNowPlayingLabel(artist: String, song: String) {
        nowPlayingLabel.text = artist + " - " + song
    }

}

