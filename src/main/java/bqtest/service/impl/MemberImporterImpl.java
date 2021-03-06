package bqtest.service.impl;

import bqtest.service.Member;
import bqtest.service.MemberImporter;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberImporterImpl implements MemberImporter {

    public List<Member> importMembers(File inputFile) throws IOException {
        List<Member> members = new ArrayList<>();
        try (BufferedReader br = new BufferedReader( new FileReader( inputFile ) )) {
            String line = br.readLine( );

            while ( line != null ) {
                String[]  splited =  line.split("\\s{2,10}");
                List<String> temp = new ArrayList<>();
                int index=0;
                for (String s:
                        splited) {
                    index++;
                    if(s.equals("\\S+")){
                        continue;
                    }
                    else if (s.length()==0)
                        continue;

                    if(numberChecker(splited[index-1])&&index-1>0&&temp.size()<6){
                        temp.set(4,splited[index-2].substring(0,splited[index-2].length()-2));
                        temp.add(splited[index-2].substring(splited[index-2].length()-2,splited[index-2].length()));
                        temp.add(splited[index-1]);

                    }else{
                        temp.add(s);
                    }
                }

                Member mem = new Member();
                mem.setId(temp.get(0).trim());
                mem.setLastName(temp.get(1).trim());
                mem.setFirstName(temp.get(2).trim());
                mem.setAddress(temp.get(3).trim());
                mem.setCity(temp.get(4).trim());
                mem.setState(temp.get(5).trim());
                mem.setZip(temp.get(6).trim());
                members.add(mem);

                line = br.readLine( );

            }
        }

        return members;
    }

    public boolean numberChecker(String s){
        if(s.length()==0| s==null){
            return false;
        }
        String regex="[0-9]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
