package edu.ap.spring.controller;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.ap.spring.service.BlockChain;
import edu.ap.spring.service.Wallet;
import edu.ap.spring.transaction.Transaction;

@Controller
public class BlockChainController {
    @Autowired
	private BlockChain bChain;
	@Autowired
	private Wallet coinbase, walletA, walletB;
	private Transaction genesisTransaction;

	@PostConstruct
	public void init() {
		bChain.setSecurity();
		coinbase.generateKeyPair();
		walletA.generateKeyPair();
		walletB.generateKeyPair();

		//create genesis transaction, which sends 100 coins to walletA:
		genesisTransaction = new Transaction(coinbase.getPublicKey(), walletA.getPublicKey(), 100f);
		genesisTransaction.generateSignature(coinbase.getPrivateKey());	 // manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; // manually set the transaction id
						
		//creating and Mining Genesis block
		Block genesis = new Block();
		genesis.setPreviousHash("0");
		genesis.addTransaction(genesisTransaction, bChain);
		bChain.addBlock(genesis);
	}
    

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/")
    public String getIndex(Model model) {
        return "index";
    }

    @PostMapping("/init")
    public String init(Model model){
        bChain.setUp();
        return "index";
    }

    @PostMapping("/balance")
    public String balance(@RequestParam("walletKey") String key, Model model) {
        Wallet wallet = bChain.getWalletFromKey(key);
        float balance = wallet.getBalance();
        model.addAttribute("balance",balance);
        return "balance";
    }

    @PostMapping("/sendFunds")
    public String sendFunds(@RequestParam("from") String from, @RequestParam("to") String to,
            @RequestParam("amount") Float amount) {
        Wallet senderWallet = bChain.getWalletFromKey(from);
        Wallet receiverWallet = bChain.getWalletFromKey(to);
        try{
            bChain.block1.addTransaction(senderWallet.sendFunds(receiverWallet.publicKey, amount), bChain.bChain);
        }catch(Exception e){}
        bChain.bChain.addBlock(bChain.block1);
        return "redirect:/";
    }
}