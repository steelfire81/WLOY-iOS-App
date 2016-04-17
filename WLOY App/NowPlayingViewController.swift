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
    let AUDIO_RATE:Float = 1.0
    let DEFAULT_ARTIST = "Artist Name"
    let DEFAULT_SHOW = "Show Name"
    let DEFAULT_SONG = "Song Name"
    let DEFAULT_DJ = "DJ Name"
    let IMAGE_ANIM_DURATION = 10.0
    let IMAGE_FEED_ADDRESS = "http://wloy.radioactivity.fm/feeds/onair.xml"
    let NOTIFICATION_BUTTON = "OK"
    let SONG_FEED_ADDRESS = "http://wloy.radioactivity.fm/feeds/last10.xml"
    let UPDATE_INTERVAL = 5.0 // length between XML updates in seconds
    let SHOW_FEED_ADDRESS = "http://wloy.radioactivity.fm/feeds/showonair.xml"
    let STREAM_ADDRESS = "http://war.str3am.com:8130/live"
    let WLOY_LOGO_ADDRESS = "wloy-logo.gif"
    
    // DATA MEMBERS
    var audioPlayer: AVPlayer!
    var songXMLParser: NSXMLParser!
    var songXMLParserDelegate: WLOYSongParserDelegate!
    var showXMLParser: NSXMLParser!
    var showXMLParserDelegate: WLOYShowParserDelegate!
    var imageXMLParser: NSXMLParser!
    var imageXMLParserDelegate: WLOYImageParserDelegate!
    var currentSongTitle:String!
    var currentArtist:String!
    var currentShow:String!
    var currentDJ:String!
    var xmlUpdateTimer:NSTimer!
    
    // OUTLETS
    @IBOutlet weak var showNameLabel: UILabel!
    @IBOutlet weak var volume: UISlider!
    @IBOutlet weak var nowPlayingLabel: UILabel!
    @IBOutlet weak var currentDJLabel: UILabel!
    @IBOutlet weak var currentArtistLabel: UILabel!
    @IBOutlet weak var imageView: UIImageView!
    
    // METHODS
    // volumeSliderMoved - called whenever the volume slider changes
    @IBAction func volumeSliderMoved(sender: AnyObject) {
        audioPlayer.volume = volume.value
    }
    
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
        
        // Initialize Image XML Parser
        imageXMLParser = NSXMLParser(contentsOfURL:NSURL(string: IMAGE_FEED_ADDRESS)!)
        imageXMLParserDelegate = WLOYImageParserDelegate()
        imageXMLParser.delegate = imageXMLParserDelegate
        checkImageXML()
        
        // Initialize image view
        updateImageArray([UIImage]())
        
        // Initialize backend connector
        BackendConnector.generateID()
        BackendConnector.sendConnectionMessage()
        BackendConnector.startTimer()
        
        // Start update timer
        startTimer()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // setCurrentShowLabel - update text on the current show field
    func setCurrentShowLabel(show: String, dj: String) {
        currentShow = show
        currentDJ = dj
        
        showNameLabel.text = show
        currentDJLabel.text = dj
    }
    
    // setNowPlayingLabel - given an artist and song, update the now playing field
    func setNowPlayingLabel(artist: String, song: String) {
        currentArtist = artist
        currentSongTitle = song
        
        nowPlayingLabel.text = song
        currentArtistLabel.text = artist
    }
    
    // startTimer - create a thread that starts listening for XML updates
    func startTimer() {
        xmlUpdateTimer = NSTimer.scheduledTimerWithTimeInterval(UPDATE_INTERVAL, target:self, selector:"timerFired", userInfo:nil, repeats:true)
    }
    
    // timerFired - check XML whenever timer fires
    func timerFired() {
        checkSongXML()
        checkShowXML()
    }
    
    // checkXML - checks XML file and updates fields to appropriate values
    func checkSongXML() {
        songXMLParser.parse()
        setNowPlayingLabel(songXMLParserDelegate.currentArtist, song:songXMLParserDelegate.currentSong)
    }
    
    // checkShowXML - checks show XML file and updates fields to appropriate values
    func checkShowXML() {
        showXMLParser.parse()
        setCurrentShowLabel(showXMLParserDelegate.currentShowName, dj:showXMLParserDelegate.currentDJ)
    }
    
    // checkImageXML - checks image XML file and updates image array
    func checkImageXML() {
        imageXMLParser.parse()
        
        // Initialize new array of images
        var imageArray = [UIImage]()
        let newImage = imageXMLParserDelegate.getImage()
        if(newImage != nil) {
            imageArray.append(newImage)
        }
        
        updateImageArray(imageArray)
    }
    
    // loadAudioPlayer - (re)loads the audio stream into audioPlayer
    func loadAudioPlayer() {
        audioPlayer = AVPlayer(URL:NSURL(string:STREAM_ADDRESS)!)
        audioPlayer.rate = AUDIO_RATE
        audioPlayer.volume = volume.value
        audioPlayer.play()
    }
    
    // sendFeedback - send like/dislike feedback to the backend server
    func sendFeedback(positive:Bool) {
        BackendConnector.sendFeedbackMessage(positive, songTitle:currentSongTitle, artist:currentArtist, show:currentShow, dj:currentDJ)
    }
    
    // sendPositiveFeedback - called when "Like" button is pressed
    @IBAction func sendPositiveFeedback(sender: AnyObject) {
        sendFeedback(true)
    }
    
    // sendNegativeFeedback - called when "Dislike" button is pressed
    @IBAction func sendNegativeFeedback(sender: AnyObject) {
        sendFeedback(false)
    }
    
    // updateImageArray - set the image view to alternate between a new array of images
    func updateImageArray(imageArray:[UIImage]) {
        imageView.stopAnimating()
        
        // Add wloy logo at the end
        var updatedImageArray = imageArray
        updatedImageArray.append(UIImage(named:WLOY_LOGO_ADDRESS)!)
        
        imageView.animationImages = updatedImageArray
        imageView.animationDuration = IMAGE_ANIM_DURATION
        imageView.startAnimating()
    }
    
    // displayNotification - show a pop-up message with the given text
    func displayNotification(message:String, title:String) {
        let notificationController = UIAlertController(title:title, message:message, preferredStyle:UIAlertControllerStyle.Alert)
        notificationController.addAction(UIAlertAction(title:NOTIFICATION_BUTTON, style:UIAlertActionStyle.Default, handler:nil))
        presentViewController(notificationController, animated:true, completion:nil)
    }
}

