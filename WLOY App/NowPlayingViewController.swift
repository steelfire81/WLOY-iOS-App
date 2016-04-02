//
//  FirstViewController.swift
//  WLOY App
//
//  Created by William Robbins on 3/15/16.
//  Copyright © 2016 William Robbins. All rights reserved.
//

import UIKit
import AVFoundation

class NowPlayingViewController: UIViewController {

    // CONSTANTS
    let DEFAULT_ARTIST = "Artist Name"
    let DEFAULT_SHOW = "Show Name"
    let DEFAULT_SONG = "Song Name"
    let DEFAULT_DJ = "DJ Name"
    let SONG_FEED_ADDRESS = "http://wloy.radioactivity.fm/feeds/last10.xml"
    let UPDATE_INTERVAL = 5 // length between XML updates in seconds
    let SHOW_FEED_ADDRESS = "http://wloy.radioactivity.fm/feeds/showonair.xml"
    let STREAM_ADDRESS = "http://war.str3am.com:8130/live"
    
    // DATA MEMBERS
    var audioPlayer: AVPlayer!
    var songXMLParser: NSXMLParser!
    var songXMLParserDelegate: WLOYSongParserDelegate!
    var showXMLParser: NSXMLParser!
    var showXMLParserDelegate: WLOYShowParserDelegate!
    
    // OUTLETS
    @IBOutlet weak var showNameLabel: UILabel!
    @IBOutlet weak var volume: UISlider!
    @IBOutlet weak var nowPlayingLabel: UILabel!
    
    // STORYBOARD ACTIONS
    // volumeSliderMoved - called whenever the volume slider changes
    @IBAction func volumeSliderMoved(sender: AnyObject) {
        audioPlayer.volume = volume.value
    }
    
    // METHODS
    override func viewDidLoad() {
        super.viewDidLoad()
        loadAudioPlayer()
        setNowPlayingLabel(DEFAULT_ARTIST, song:DEFAULT_SONG)
        setCurrentShowLabel(DEFAULT_SHOW, dj:DEFAULT_DJ)
        
        // Initialize Song XML Parser
        songXMLParser = NSXMLParser(contentsOfURL:NSURL(string: SONG_FEED_ADDRESS)!)
        songXMLParserDelegate = WLOYSongParserDelegate()
        songXMLParser.delegate = songXMLParserDelegate
        checkSongXML()
        
        // Initialize Show XML Parser
        showXMLParser = NSXMLParser(contentsOfURL:NSURL(string: SHOW_FEED_ADDRESS)!)
        showXMLParserDelegate = WLOYShowParserDelegate()
        showXMLParser.delegate = showXMLParserDelegate
        checkShowXML()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // setCurrentShowLabel - update text on the current show field
    func setCurrentShowLabel(show: String, dj: String) {
        if(show == "") {
            showNameLabel.text = dj
        }
        else {
            showNameLabel.text = show + " - " + dj
        }
    }
    
    // setNowPlayingLabel - given an artist and song, update the now playing field
    func setNowPlayingLabel(artist: String, song: String) {
        nowPlayingLabel.text = artist + " - " + song
    }
    
    // startTimerThread - create a thread that starts listening for XML updates
    func startTimerThread() {
        // TODO: Actually get this to work
        NSLog("Timer thread started! (not really)")
    }
    
    // checkXML - checks XML file and updates fields to appropriate values
    func checkSongXML() {
        songXMLParser.parse()
        setNowPlayingLabel(songXMLParserDelegate.currentArtist, song:songXMLParserDelegate.currentSong)
    }
    
    // checkShowXML - checks show XML file and updates fields to appropriate values
    func checkShowXML() {
        showXMLParser.parse()
        setCurrentShowLabel(showXMLParserDelegate.currentShowName, dj:showXMLParserDelegate.currentDJ) // TODO: Put show DJ somewhere
    }
    
    // loadAudioPlayer - (re)loads the audio stream into audioPlayer
    func loadAudioPlayer() {
        audioPlayer = AVPlayer(URL:NSURL(string:STREAM_ADDRESS)!)
        audioPlayer.rate = 1.0
        audioPlayer.volume = volume.value
        audioPlayer.play()
    }
}

