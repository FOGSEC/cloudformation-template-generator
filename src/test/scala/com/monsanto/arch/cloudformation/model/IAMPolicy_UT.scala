package com.monsanto.arch.cloudformation.model

import com.monsanto.arch.cloudformation.model.resource._
import ValidPolicyCombo._
import org.scalatest.{Matchers, FunSpec}


/**
 * Created by djdool on 7/9/15.
 */
class IAMPolicy_UT extends FunSpec with Matchers {
  describe("AWS::IAM::Policy") {
    it("should create object from roles") {
      val instanceRole = `AWS::IAM::Role`(
        name = "S3Role",
        AssumeRolePolicyDocument =
          PolicyDocument(
            Statement = Seq(
              PolicyStatement(
                Effect = "Allow",
                Principal = Some(DefinedPrincipal(Map("Service" -> Seq("ec2.amazonaws.com")))),
                Action = Seq("sts:AssumeRole")
              )
            )
          ),
        Path = Some("/")
      )
      val policyForRoles = `AWS::IAM::Policy`.from(
        "S3GetPolicy",
        PolicyDocument(
          Statement = Seq(
            PolicyStatement(
              Effect = "Allow",
              Action = Seq("s3:GetObject"),
              Resource = Some("/*")
            )
          )
        ),
        "StaxS3Access",
        None,
        Some(Seq(ResourceRef(instanceRole))),
        None
      )
    }
  }
}
